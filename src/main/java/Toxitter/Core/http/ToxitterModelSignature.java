package Toxitter.Core.http;

import Toxitter.Model.elemental.sky.ReplenisherStack;

import java.lang.reflect.Parameter;

public class ToxitterModelSignature
{
    public Class toxiClass;
    public static class Method
    {
        public java.lang.reflect.Method method;
        Parameter p;
        public String name;
        String value = null;
        boolean obligatory = false;

        public Method getClone()
        {
            Method m = new Method();
            m.method = this.method;
            m.p = this.p;
            m.name = this.name;
            m.value = this.value;
            m.obligatory = this.obligatory;
            return m;
        }

        @Override
        public boolean equals(Object o)
        {
            if ( o.getClass().equals(String.class) )
            {
                return o.equals(name);
            }
            if ( o.getClass().equals(Method.class) )
            {
                return ((Method)o).name.equals(name);
            }
            return false;
        }
    }
    private ReplenisherStack<Method> methods = new ReplenisherStack<>();

    public Method getMethod()
    {
        return methods.peek();
    }

    public void addMethod(java.lang.reflect.Method method, Parameter p, String requestParamName, boolean obligatory)
    {
        Method m = new Method();
        m.p = p;
        m.method = method;
        m.name = requestParamName;
        m.obligatory = obligatory;
        methods.push(m);
    }

    public void execute()
    {
        releaseForNextRequest();
    }

    /**
     * Checks if all obligatory values for the route are present.
     * @return
     */
    public boolean isComplete()
    {
        methods.replenish();
        while(methods.getCount()>0)
        {
            if ( methods.peek().obligatory && methods.peek().value == null )
            {
                return false;
            }
            methods.pop();
        }
        return true;
    }

    /**
     * Fills all values with null values.
     * This means, they can be filled with values again,
     * to check if all obligatory values of a route are present
     */
    public void releaseForNextRequest()
    {
        methods.replenish();
        while(methods.getCount()>0)
        {
            methods.peek().value = null;
            methods.pop();
        }
        methods.replenish();
    }

    public Object[] splurpIntoParameters()
    {
        methods.replenish();
        Object[] args = new Object[methods.getCount()];
        while(methods.getCount()>0)
        {
            System.out.println("Argument: Value "+methods.peek().value);
            args[methods.getCount()-1] = methods.peek().value;
            methods.pop();
        }
        return args;
    }

    /**
     * @return a clone of this Modelsignature that also has a clone of the data.
     * The way it works: ToxitterModelSignature is empty, then filled with values from the request,
     * then gets emptied again. If one wants to save the data from the signature to reference them
     * later on, one must make a clone of the model-signature
     */
    public ToxitterModelSignature getClone()
    {
        ToxitterModelSignature copy = new ToxitterModelSignature();
        ReplenisherStack<Method> clone = new ReplenisherStack<>();
        methods.replenish();
        while(methods.getCount()>0)
        {
            clone.push(methods.peek().getClone());
            methods.pop();
        }
        methods.replenish();
        copy.methods = clone;
        copy.toxiClass = this.toxiClass;
        return copy;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        methods.replenish();
        while(methods.getCount()>0)
        {
            System.out.println("TOXITTERMODEL SIGNATURE: KEY "+methods.peek().name+" VALUE "+methods.peek().value);
            sb.append("\"").append(methods.peek().name).append("\" : ").append("\"").append(methods.peek().value).append("\",\n");
            methods.pop();
        }
        sb.delete(sb.length()-2,sb.length());
        sb.append("\n}");
        return sb.toString();
    }

    public ReplenisherStack<Method> getReplenisherStack()
    {
        return this.methods;
    }
}
