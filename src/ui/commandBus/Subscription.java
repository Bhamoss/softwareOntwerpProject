package ui.commandBus;


import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import ui.WindowCompositor;
import ui.commands.UICommand;
import ui.widget.Widget;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A class representing a subscription with an object and a method to invoke on it.
 *
 * @author Thomas Bamelis
 *
 * @version 0.0.1
 */
class Subscription {

    /**
     * Creates a subscription object with the given subscriber and method.
     *
     * @param subscriber
     *          The subscriber.
     *
     * @param onEvent
     *          The method called on subscriber with the given command when triggered.
     *
     * @post If subscriber and event are valid, a subscription object with them is created.
     *          | if(isValidSubscriber() && canHaveAsOnEvent()):
     *          |    this.getSubscriber() == subscriber
     *          |    this.getOnEvent() == onEvent
     *
     * @throws IllegalArgumentException
     *          If the given subscriber or event is not valid.
     *              | if(!isValidSubscriber() || !canHaveAsOnEvent())
     */
    @Raw
    Subscription(Object subscriber, Method onEvent)
            throws IllegalArgumentException
    {
        if(!isValidSubscriber(subscriber)) {
            throw new IllegalArgumentException("The subscriber is invalid.");
        }

        this.subscriber = subscriber;

        // Checking if the subscriber and onEvent are valid.

        if (!canHaveAsOnEvent(onEvent)) {
            throw new IllegalArgumentException("The event is invalid.");
        }

        // Assigning the final variables.

        this.onEvent = onEvent;
    }

    /**
     * Triggers the onEvent method on the subscriber with parameter command.
     *
     * @param command
     *          The command to be passed in the onEvent method.
     *
     * @post    The onEvent method is called on the subscriber with the command parameter if command is valid.
     *              | if(canBeParameter(command):
     *              |    subscriber.onEvent(command)
     *
     * @throws IllegalArgumentException
     *          If the command is not valid.
     *              | !canBeParameter(command)
     */
    void trigger(UICommand command) throws IllegalArgumentException
    {
        if (!canBeParameter(command))
            throw new IllegalArgumentException("The given command is invalid.");
        // if all invariants and checkers are satisfied, this should not never give an error.
        try {
            getOnEvent().invoke(getSubscriber(), command);
        }
        catch (Exception e)
        {
            // THIS SHOULD NEVER HAPPEN
            // A try catch statement is required for compilation.
            throw new IllegalStateException();
        }
    }


    /**
     *
     * Returns whether or not the command can be used as a parameter when onEvent is called on Subscriber.
     *
     * @param command
     *          The command to be checked.
     * @return True if and only if the command is of the same class as the parameter or a subclass of it of onEvent and command is not null.
     *          | result == command != null && getOnEvent().getParameterTypes()[0].isAssignableFrom(command.getClass())
     */
    @Model
    private boolean canBeParameter(UICommand command)
    {
        // command can not be null
        if(command == null) return false;


        // command has to be of the same parameter type as on
        if(!getOnEvent().getParameterTypes()[0].isAssignableFrom(command.getClass())) return false;

        return true;
    }



    /**
     * The method to invoke when an event happens.
     *
     * @invar The method only has one parameter of (sub)class (of) UICommand.
     * @invar The method has an @Subscribe annotation.
     * @Invar The method is a method of a superclass/the class of the object.
     * @Invar The method is not null.
     * @Invar The method is public.
     */
    private final Method onEvent;

    /**
     * Returns the class of the parameter of onEvent.
     * @return the class of the parameter of onEvent.
     *          | getOnEvent().getParameterTypes()[0]
     */
    Class<?> getOnEventClass()
    {
        return getOnEvent().getParameterTypes()[0];
    }

    /**
     * Returns the event of this subscriber.
     *
     * @return The event of this subscriber.
     *
     */
    @Model @Raw @Basic
    private final Method getOnEvent() {
        return onEvent;
    }

    /**
     * Returns whether or not the subscription can have this event
     *
     * @param event
     *          The method to be tested.
     *
     * @return  True if and only if all of the following are true:
     *              - event is not null
     *              - event has @Subscribe as its annotation
     *              - event is a public method
     *              - event has exactly 1 parameter
     *              - the parameter of event is of (a subclass of) class UICommand
     */
    @Model
    private boolean canHaveAsOnEvent(Method event)
    {
        // if the method is null, return false
        if(event == null) {
            return false;
        }

        // if the method has no @Subscribe annotation, return false
        if(!event.isAnnotationPresent(Subscribe.class)) {
            return false;
        }

        // if the method is not public, return false
        if (!Modifier.isPublic(event.getModifiers())) {
            return false;
        }

        // if the method does not have exactly one parameter, return false.
        if(event.getParameterCount() != 1) {
            return false;
        }

        // if the parameter of the method is not (a subclass of) UICommand, return false.
        if(!UICommand.class.isAssignableFrom(event.getParameterTypes()[0])) {
            return false;
        }


        // LEAVING THIS HERE, THE OTHER IMPLEMENTATION SHOULD WORK, BUT JUST IN CASE.
        /*
        // if the method is no method which can be used on the subscriber, return false.
        boolean isReachableMethod = false;
        Class<?> currentClass = getSubscriber().getClass();
        // navigate the object tree back to object to see if subscriber or one of its subclasses declared the method.
        while (currentClass != null && !isReachableMethod) {
            // IMPORTANT
            // getMethods() only returns the public methods, this returns all, I can change this later
            Method[] subscribeMethods = currentClass.getDeclaredMethods();
            for (Method method : subscribeMethods) {
                if (method.equals(event)) isReachableMethod = true;
            }
            currentClass = currentClass.getSuperclass();
        }

        if(!isReachableMethod) {
            return false;
        }
        */

        // what getMethods() does:
        // Returns an array containing Method objects reflecting all the public methods of the
        // class or interface represented by this Class object, including those declared by the
        // class or interface and those inherited from superclasses and superinterfaces.
        // So because we only allow public methods this should work
        boolean isReachableMethod = false;
        for (Method method :
                getSubscriber().getClass().getMethods()) {
            if (method.equals(event))
            {
                isReachableMethod = true;
                break;
            }
        }
        if(!isReachableMethod) {
            return false;
        }

        return true;
    }


    /**
     * The subscriber on which to invoke a method when a command to which it is subscribed is put on the bus.
     *
     * @invar subscriber is not null.
     * @invar subscriber is a (subclass of) WindowCompositor or Widget.
     */
    private final Object subscriber;

    /**
     * Returns whether or not the given object is the same as the subscriber of this subscription.
     * @param object
     *          The object to be tested.
     *
     * @return  True if and only if the object is the same object as the subscriber of this subscription.
     *          | getSubscriber() == widget
     */
    boolean isSubscriber(Object object)
    {
        return getSubscriber() == object;
    }

    /**
     * Returns the subscriber.
     *
     * @return The subscriber.
     *
     */
    @Model @Raw @Basic
    private final Object getSubscriber()
    {
        return subscriber;
    }

    /**
     *
     * Returns whether or not the subscriber is valid.
     *
     * @param sub
     *          The object to be checked.
     *
     * @return True if and only if sub is not null and a subclass of WindowCompositor or Widget.
     *          | return == sub != null
     *          |           && (WindowCompositor.class.isAssignableFrom(sub.getClass()) || Widget.class.isAssignableFrom(sub.getClass()))
     */
    @Model
    private boolean isValidSubscriber(Object sub)
    {
        // sub cannot be null
        if (sub == null) return false;

        // sub must be (a subclass of) WindowCompositor or Widget
        if(!(WindowCompositor.class.isAssignableFrom(sub.getClass()) || Widget.class.isAssignableFrom(sub.getClass()))) return false;

        return true;
    }


}
