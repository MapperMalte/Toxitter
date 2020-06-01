package Toxitter.Core;

import theory.ReplenisherStack;

import java.lang.reflect.Parameter;

public class ToxitterModelSignature
{
    Class toxiClass;
    public static class Method
    {
        java.lang.reflect.Method method;
        Parameter p;
        String name;
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
        replenish();
    }

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

    public void replenish()
    {
        methods.replenish();
        while(methods.getCount()>0)
        {
            methods.peek().value = null;
            methods.pop();
        }
        methods.replenish();
    }

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
