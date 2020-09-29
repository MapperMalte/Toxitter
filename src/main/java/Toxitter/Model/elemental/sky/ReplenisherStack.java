package Toxitter.Model.elemental.sky;

/**
 *
 * @author m.nolden
 */
public class ReplenisherStack<T> extends SimpleStack<T> {
    private SimpleStack<T> garbage = new SimpleStack<>();
    private int count = 0;

    public int getCount()
    {
        return this.count;
    }

    public T find(Object data)
    {
        replenish();
        while ( this.count > 0 )
        {
            if ( this.peek().equals(data) )
            {
                return this.peek();
            }
            this.pop();
        }
        return null;
    }

    /**
     *
     * @param data
     */
    @Override
    public void push(T data)
    {
        super.push(data);
        count = count + 1;
    }

    @Override
    public void pop()
    {
        garbage.push(peek());
        count = count - 1;
        super.pop();
    }


    public void popAndNeverReplenish()
    {
        count = count - 1;
        super.pop();
    }

    public void replenish()
    {
        while ( !garbage.empty() )
        {
            push(garbage.peek());
            garbage.pop();
        }
    }
}