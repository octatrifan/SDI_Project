package repo.Sorting;

import exception.RepoException;
import service.AService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Sort class for the sorting infrastructure
 *
 * @author Liviu.
 *
 */

// Direction enum for sorting
enum Direction
{
    ASCENDING,
    DESCENDING
}

public class Sort
{
    private List<Pair<Direction, String>> parameters;

    public Sort()
    {
        this.parameters = new ArrayList<>();
    }

    public Sort by(String fieldName) {
        parameters.add(new Pair<Direction, String>(Direction.ASCENDING, fieldName));
        return this;
    }

    public Sort descending() throws RepoException
    {
        if(parameters.size() < 1)
        {
            throw new RepoException("Invalid descending for sorting! Field not found");
        }
        Pair<Direction, String> lastPair = parameters.get(parameters.size() - 1);
        lastPair.setFirst(Direction.DESCENDING);
        parameters.set(parameters.size() - 1, lastPair);
        return this;
    }

    public Sort and(Sort other)
    {
        Sort result = new Sort();
        List<Pair<Direction, String>> parameters = new ArrayList<>(this.parameters);
        parameters.addAll(other.getParameters());
        result.setParameters(parameters);
        return result;
    }

    public void setParameters(List<Pair<Direction, String>> parameters)
    {
        this.parameters = parameters;
    }

    public List<Pair<Direction, String>> getParameters()
    {
        return parameters;
    }

    public <T> Iterable<T> sort(Iterable<T> elements)
    {
        return StreamSupport.stream(elements.spliterator(), false)
                .sorted(new SortComparator())
                .collect(Collectors.toList());
    }

    private class SortComparator implements Comparator<Object>
    {
        public int compareCustom(Object first, Object second, Pair<Direction, String> criteria)
        {
            Object firstValue = null;
            try
            {
                firstValue = getValueByFieldName(first, criteria.getSecond());
                Object secondValue = getValueByFieldName(second, criteria.getSecond());
                int result = ((Comparable) firstValue).compareTo(secondValue);
                if (criteria.getFirst() == Direction.ASCENDING)
                    return result;
                else
                    return -result;
            } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
            {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        public int compare(Object first, Object second)
        {
            return parameters.stream()
                    .map(entry -> compareCustom(first, second, entry))
                    .filter(value -> value != 0)
                    .findFirst()
                    .orElse(0);
        }
    }

    private Object getValueByFieldName(Object element, String field) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException
    {
        // Get the class of the element using reflection
        Class<?> c = Class.forName(element.getClass().getName());
        // Construct the getter name from the given field
        String methodName = field;
        methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        Method method = c.getDeclaredMethod(methodName);
        // Return the value returned by the getter
        return method.invoke(element);
    }
}