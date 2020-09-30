package Toxitter.Core.Elemental.wind;

public class SimpleStack<T>
{
    private ValueBag top;

    private class ValueBag
    {
        ValueBag previous = null;
        T content = null;
    }

    public void push(T element)
    {
        ValueBag b = new ValueBag();
        b.content = element;
        if ( top == null )
        {
            top = b;
        } else
        {
            b.previous = top;
            top = b;
        }
    }

    public void pop()
    {
        if ( top != null )
            top = top.previous;
    }

    public void nPush(SimpleStack<T>[] s)
    {
        while(!this.empty())
        {
            for(int i = 0; i < s.length; i++)
            {
                s[i].push(this.peek());
            }
            this.pop();
        }
    }

    /**
     *
     * @return the top of the stack
     */
    public T peek()
    {
        return top.content;
    }

    public boolean empty()
    {
        return (top==null);
    }
}