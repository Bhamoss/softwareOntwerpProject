package window.commandBus;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import window.commands.UICommand;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

public class CommandBus {

    //TODO: test this class

    /**
     * Creates a new CommandBus instance with no subscriptions.
     *
     * @post the bus does not have any subscriptions.
     */
    @Raw
    public CommandBus() {
        subscriptions = new HashMap<>();
    }




    /**
     * The subscriptions of this commandBus
     */
    private Map<Class<?>, List<Subscription>> subscriptions;


    //TODO: comments
    /**
     *
     * @param subscriber
     * @throws IllegalArgumentException
     */
    public void subscribe(Object subscriber) throws IllegalArgumentException {

        //TODO: use the cache

        // Iterate the public methods of the subscriber class and all of its subclasses.
        // we only subscribe to public methods, so this should suffice. Check the method documentation.
        for (Method method :
                subscriber.getClass().getMethods()) {

            // check for methods which have the @Subscribe annotation
            if(method.isAnnotationPresent(Subscribe.class))
            {
                // get to which command the method is subscribed.
                Class<?> commandClass = method.getParameterTypes()[0];

                // if there is not yet a subscriptionlist for this class of command, create one
                if (!getSubscriptions().containsKey(commandClass))
                {
                    getSubscriptions().put(commandClass, new ArrayList<Subscription>());
                }

                // add the new subscription for this subscriber and method to the right subscription list
                // THIS METHOD THROWS AN ILLEGALARGUMENTEXCEPTION IF YOU DID NOT FOLLOW TO RULES IN @SUBSCRIBE USAGE
                getSubscriptions().get(commandClass).add(new Subscription(subscriber, method));

            }
        }
    }

    //TODO: comment

    /**
     *
     * @param subscriber
     */
    public void unsubscribe(Object subscriber)
    {
        //TODO: only iterate over the subscription lists of the commandsclasses of the first parameter of the subscriberCache(subscriber.class)

        // iterate over all subscription lists of the commands.
        for (List<Subscription> subscriptionList :
                getSubscriptions().values()) {

            // iterate over the subscriptions in this subscription list.
            for (Subscription subscription :
                    subscriptionList) {

                // if the subscriber of this subscription is subscriber,
                // remove this subscription from this subscriptionlist
                if (subscription.isSubscriber(subscriber)) subscriptionList.remove(subscription);
            }
        }
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
            for (Subscription subscription :
                    subscriptions.get(commandClass)) {
                subscription.trigger(command);
            }
        }
    }


    /**
     * Returns the subscriptions of this CommandBus.
     * @return the subscriptions of this CommandBus.
     */
    @Basic @Raw
    private Map<Class<?>, List<Subscription>> getSubscriptions() {
        return subscriptions;
    }

    //TODO: hashmap <class, list of methods> which holds the subscription methods for a class
    // it would function as a cache to speed up when an object subscribes.
    private Map<Class<?>, List<Method>> subscriberCache;

    private Map<Class<?>, List<Method>> getSubscriberCache()
    {
        return subscriberCache;
    }
}
