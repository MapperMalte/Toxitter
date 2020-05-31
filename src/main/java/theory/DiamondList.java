/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theory;

import java.util.logging.Level;
import java.util.logging.Logger;


public class DiamondList<T>
{
    private ValueBag pointer;
    private ValueBag bottom;
    private ValueBag top;
    private SimpleStack<ValueBag> breakpoints;
    private int size;

    /**
     * Adds an element to the top of the list
     * @param element the element to be added
     */
    public void addOnTop(T element)
    {
        if ( empty() )
        {
            bottom = new ValueBag();
            bottom.content = element;
            top = bottom;
            pointer = bottom;
        } else
        {
            ValueBag newBag = new ValueBag();
            newBag.content = element;
            top.next = newBag;
            newBag.previous = top;
            top = newBag;
        }
        size++;
    }

    /**
     * @return true if the list is empty
     */
    public boolean empty()
    {
        return (bottom==null);
    }

    /**
     * Inserts an element before the element the pointer currently points at
     * The pointer then points to the new element.
     * @param element element to be inserted
     */
    public void insertBeforePointer(T element)
    {
        if ( !empty() && pointer != null )
        {
            ValueBag newBag = new ValueBag();
            newBag.content = element;

            if ( pointer == bottom )
            {
                bottom = newBag;
                bottom.next = pointer;
                pointer.previous = bottom;
            } else
            {
                newBag.previous = pointer.previous;
                pointer.previous.next = newBag;

                newBag.next = pointer;
                pointer.previous = newBag;
            }
        }
        size++;
    }

    /**
     * Returns the element the pointer currently points at. If the
     * list has at least one element, this will return the bottom element, unless
     * movePointerToNext() has been called
     * @return
     */
    public T getCurrent()
    {
        return pointer.content;
    }

    /**
     * Removes the element the pointer points to currently.
     * Then sets the pointer to the element that now takes the old element's place.
     */
    public void removeCurrent()
    {
        if ( !empty() && (pointer != null) )
        {
            size--;
            if ( top == bottom)
            {
                pointer = null;
                top = null;
                bottom = null;
            } else if ( pointer == top )
            {
                pointer.previous.next = null;
                pointer = pointer.previous;
                top = pointer;
            } else if ( pointer == bottom )
            {
                pointer.next.previous = null;
                pointer = pointer.next;
                bottom = pointer;
            } else
            {
                pointer.next.previous = pointer.previous;
                pointer.previous.next = pointer.next;
                pointer = pointer.next;
            }
        }
    }

    /**
     * Makes the pointer point at its previous next element. If it already is at the top, this method
     * makes the pointer point at null.
     */
    public void next()
    {
        if ( !empty() && pointer != null )
        {
            pointer = pointer.next;
        }
    }

    /**
     * Makes the pointer point at its previous element. If it already is at the bottom, this method makes
     * the pointer point at null
     */
    public void previous()
    {
        if ( !empty() && pointer != null )
        {
            pointer = pointer.previous;
        }
    }

    /**
     * Moves the pointer to the bottom of the list
     * Use this method if you want to prepare for a while loop across all elements of the list
     */
    public void bottom()
    {
        pointer = bottom;
    }

    /**
     * Moves the pointer to the top of the list
     */
    public void top()
    {
        pointer = top;
    }

    /**
     * @return whether pointer is at top of list
     */
    public boolean isPointerAtTop()
    {
        return (pointer==top);
    }

    /**
     * @return whether pointer is at bottom of list
     */
    public boolean isPointerAtBottom()
    {
        return (pointer==bottom);
    }

    /**
     * Removes all elements from the list that are between the pointer and the top breakpoint,
     * excluding pointer and breakpoint. Does nothing if breakpoint is empty. Leaves the breakpoint on the stack.
     */
    public void clip()
    {
        if ( breakpoints != null )
        {
            if ( breakpoints.empty() == false )
            {
                ValueBag b = breakpoints.peek();
                b.next = pointer;
                pointer.previous = b;
            }
        }
    }

    /**
     * Returns whether the pointer is currently null.
     * Use this as a stop condition for loops through the list since
     * the pointer will become null if it is set to the "next" element of the top.
     * @return
     */
    public boolean isPointerNull()
    {
        return (pointer==null);
    }

    /**
     * Moves the pointer to the element if the list contains it.
     * If you want to keep your pointer, use the pointer-stack
     *
     * NOTE: This has linear runtime O(n)
     * NOTE: This checks for object-equality, not for content-equality
     *
     * @return true if the element is contained, false otherwise
     */
    public boolean movePointerToValue(T element)
    {
        bottom();
        while ( !isPointerNull() )
        {
            if ( getCurrent().equals(element) )
            {
                return true;
            }
            next();
        }
        return false;
    }

    /**
     * pushes the current pointer on a breakpoint stack
     * calling popBreakpoint will set the pointer to the breakpoint on top
     * of the stack and then remove that breakpoint from the stack.
     * Use for inner loops
     */
    public void pushBreakpoint()
    {
        if ( breakpoints == null )
        {
            breakpoints = new SimpleStack<ValueBag>();
        }
        breakpoints.push(pointer);
    }

    /**
     * Sets the pointer to the breakpoint that is on top of the breakpointstack, unless
     * the stack is empty.
     */
    public void popBreakpoint()
    {
        if ( breakpoints != null )
        {
            if ( !breakpoints.empty() )
            {
                pointer = breakpoints.peek();
                breakpoints.pop();
            }
        }
    }

    /**
     * Concats this list with the parameter. The parameter list comes after this list.
     *
     * @param list the list
     */
    public void concatLists(DiamondList<T> list)
    {
        if ( !list.empty() )
        {
            top.next = list.bottom;
            list.bottom.previous = top;
        }
    }

    public boolean contains(T item)
    {
        this.pushBreakpoint();
        this.bottom();
        if ( empty() ) return false;
        while ( !this.isPointerNull() )
        {
            if ( this.getCurrent().equals(item) )
            {
                this.popBreakpoint();
                return true;
            }
            this.next();
        }
        this.popBreakpoint();
        return false;
    }

    public int getSize()
    {
        return size;
    }

    private class ValueBag
    {
        ValueBag previous = null;
        ValueBag next = null;
        T content = null;
    }
}