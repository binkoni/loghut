package company.gonapps.loghut.filter;

public interface Filter<T> {
    boolean test(T target); 
}
