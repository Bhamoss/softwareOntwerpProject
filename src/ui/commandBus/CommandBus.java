package ui.commandBus;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import ui.commands.UICommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class modeled after the eventBus pattern usable only for commands and widgets or windowCompositor, pretty well optimized if I do say so myself.
 * The implementation follows after the standard Android application eventBus model.
 *
 * @author Thomas Bamelis
 * @version 0.0.1
 */
public class CommandBus {


    /**
     * Creates a new CommandBus instance with no subscriptions.
     *
     * @post the bus does not have any subscriptions.
     */
    @Raw
    public CommandBus() {
        // Initialize variables without any subscriptions.
        subscriptions = new HashMap<>();
        subscriberCache = new HashMap<>();
        subscriptionCache = new HashMap<>();
    }




    /**
     * The subscriptions of this commandBus
     */
    private Map<Class<?>, Map<Subscription, Subscription>> subscriptions;



    /**
     * Subscribes the subscriber so that its own and Inherited methods annotated with @Subscribe are triggered when
     * someone posts a UICommand subclass corresponding to the parameter of the method on this bus.
     * The methods notated with @Subscribe have to fullfill the following rules:
     *      - method must be public
     *      - must have exactly 1 parameter
     *      - the parameter has to be (a subclass of) UICommando
     * The subscriber must be a Widget or WindowCompositor (subclass) object.
     *
     * @param subscriber
     *          The object which wants to subscribe.
     *
     * @post when no IllegalArgumentException is thrown, when a command is posted to this commandBus and the subscriber
     *          has a method with the class of the command as only parameter, the method is called on the subscriber
     *          with the command as parameter.
     *              | for each method in subscriber.class.getAllMethods where method.isAnnotationPresent(Subscribe.class) :
     *              | if(this.post(command) && method.getParameterTypes()[0] == command.getClass()) {
     *              |    subscriber.method(command)
     *              |}
     *
     * @throws IllegalArgumentException
     *          If the subscriber is not valid or its own and inherited methods annotated with @Subscribe are invalid
     *          or the subscriber is already subscribed or the subscriber does not have any @Subscribe methods.
     *          | if NOT:
     *          |   for all methods in subscriber.class.getMethods() where method.isAnnotationPresent(Subscribe.class):
     *          |       Subscription.isValidSubscriber(subscriber) && Subscriber.canHaveAsOnEvent(method)
     *          |       && subscriber is not yet subscribed
     *          | AND there is at least one method with the @Subscribe tag
     */
    public void subscribe(Object subscriber) throws IllegalArgumentException {

        // placeholder for in loops
        Subscription tempSub;

        // boolean to later check if there were any methods annotated with @Subscription
        boolean hasSubscriptionMethods = false;

        // check if the subscriber is not already subscribed
        if(getSubscriptionCache().containsKey(subscriber)) throw new IllegalArgumentException("The subscriber is already subscribed.");

        // a list to later add to the subscription cache
        List<Subscription> newSubs = new ArrayList<>();

        // if the class has been seen before, use the subscriber cached methods for it, otherwise construct it from scratch and add it to the subscriber cache
        if(getSubscriberCache().containsKey(subscriber.getClass()))
        {
            // methods do not change at runtime, so if you have seen a class once, you do not have to search it again.
            for (Method method :
                    getSubscriberCache().get(subscriber.getClass())) {
                // get to which command the method is subscribed.
                Class<?> commandClass = method.getParameterTypes()[0];


                // if there is not a subscriptionlist for this class of command, create one
                // has to be here because the keys can be deleted when unsubscribing
                if (!getSubscriptions().containsKey(commandClass)) {
                    getSubscriptions().put(commandClass, new HashMap<>());
                }

                // add the new subscription for this subscriber and method to the right subscription list
                tempSub = new Subscription(subscriber, method);
                // JAVA uses the memory address of the object to hash on, so this makes sense
                getSubscriptions().get(commandClass).put(tempSub, tempSub);

                // add it to the subscription cache
                newSubs.add(tempSub);

            }
        }
        else {

            // because the class has not been seen yet, create a list for its methods.
            getSubscriberCache().put(subscriber.getClass(), new ArrayList<>());

            // Iterate the public methods of the subscriber class and all of its subclasses.
            // we only subscribe to public methods, so this should suffice. Check the method documentation.
            for (Method method :
                    subscriber.getClass().getMethods()) {

                // check for methods which have the @Subscribe annotation
                if (method.isAnnotationPresent(Subscribe.class)) {
                    hasSubscriptionMethods = true;
                    // get to which command the method is subscribed.
                    Class<?> commandClass = method.getParameterTypes()[0];

                    // if there is not yet a subscriptionlist for this class of command, create one
                    if (!getSubscriptions().containsKey(commandClass)) {
                        getSubscriptions().put(commandClass, new HashMap<>());
                    }

                    // add the new subscription for this subscriber and method to the right subscription list
                    // THIS METHOD THROWS AN ILLEGALARGUMENTEXCEPTION IF YOU DID NOT FOLLOW TO RULES IN @SUBSCRIBE USAGE
                    tempSub = new Subscription(subscriber, method);
                    // JAVA uses the memory address of the object to hash on, so this makes sense
                    getSubscriptions().get(commandClass).put(tempSub, tempSub);

                    // add the method to the cache
                    getSubscriberCache().get(subscriber.getClass()).add(method);

                    // add it to the subscription cache
                    newSubs.add(tempSub);
                }
            }

            // if there were no methods annotated with @Subscribe, throw an error
            if (!hasSubscriptionMethods)
                throw new IllegalArgumentException("The object does not have subscription methods.");
        }

        // add the new subscriptions to the subscription cache
        getSubscriptionCache().put(subscriber, newSubs);
    }


    /**
     * Unsubscribes the subscriber so its @Subscribe methods are no longer called when a command gets posted to this bus.
     * You must unsubscribe when terminating an object because this can leave "dangling pointers" here.
     *
     * @param   subscriber
     *          The subscriber to unsubscribe.
     *
     * @post    unsubscribes the subscriber if it is subscribed.
     *          | if(this.subscribe(subscriber)) {
     *          |    for all commands in getSubscription:
     *          |        for all subscriptions in getSubscriptions().get(command):
     *                       !subscription.isSubscriber(subscriber)
     *          |}
     *
     * @throws  IllegalArgumentException
     *          If the subscriber is not subscribed yet.
     *          |   !this.subscribe(subscriber)
     */
    public void unsubscribe(Object subscriber)
        throws IllegalArgumentException
    {

        // get the class of the subscriber
        Class<?> subClass = subscriber.getClass();

        // if the class has never been subscribed, throw error
        if(!getSubscriberCache().containsKey(subClass)) throw new IllegalArgumentException("This class has not been subscribed.") ;

        // if the subscriber is not subscribed throw error
        if (!getSubscriptionCache().containsKey(subscriber))
            throw new IllegalArgumentException("The subscriber is not subscribed.") ;

        // iterate over the subscriptions of this subscriber
        for (Subscription sub :
                getSubscriptionCache().get(subscriber)) {

            // get the class of the command of which the subscription is triggered.
            Class<?> commandClass = sub.getOnEventClass();

            // remove the subscription
            getSubscriptions().get(commandClass).remove(sub);

            // if there are no subscription left for this command, remove its entry to keep the map as small as possible
            if(getSubscriptions().get(commandClass).size() == 0) getSubscriptions().remove(commandClass);
        }

        //Remove the subscribercache
        getSubscriptionCache().remove(subscriber);
    }


    /**
     *
     * Triggers the subscriptions for this command, with the command as parameter.
     *
     * @param command
     *          The command of which you want to trigger its subscribers.
     *
     * @post The subscribers of the command are triggered, with the command as parameter.
     *          | for each subscription in getSubscriptions().get(command): subscription.trigger(command)
     */
    public void post(UICommand command) {

        // Get the exact class of the command
        Class<?> commandClass = command.getClass();

        //TODO: trigger all subscribe methods of superClasses?
        // you can do this with the following
        // while(!commandClass.equals(Object.class)) { ...; commandClass = commandClass.getSuperClass()}
        // because UICommando is a direct subclass of the Object class

        // If there are subscriptions for this method, trigger them.
        if (subscriptions.containsKey(commandClass)) {

            // iterate over the subscriptions for this command
            for (Subscription subscription :
                    subscriptions.get(commandClass).values()) {

                // trigger subscription with the command
                subscription.trigger(command);
            }
        }
    }


    /**
     * Returns the subscriptions of this CommandBus.
     * @return the subscriptions of this CommandBus.
     */
    @Basic @Raw
    private Map<Class<?>, Map<Subscription,Subscription>> getSubscriptions() {
        return subscriptions;
    }

    // used for subscribe and unsubscribe speedup
    /**
     * A variable used for caching the methods of a class which are used as subscription methods.
     */
    private Map<Class<?>, List<Method>> subscriberCache;

    /**
     * Return the subscriberCache.
     * @return subscriberCache
     */
    @Basic @Raw
    private Map<Class<?>, List<Method>> getSubscriberCache()
    {
        return subscriberCache;
    }

    // used for unsubscribe speedup
    /**
     * A variable used for caching the subscriptions of a subscriber.
     */
    private Map<Object, List<Subscription>> subscriptionCache;

    /**
     * Return the subscriptionCache.
     * @return subscriptionCache
     */
    @Basic @Raw
    private Map<Object, List<Subscription>> getSubscriptionCache()
    {
        return subscriptionCache;
    }
}
