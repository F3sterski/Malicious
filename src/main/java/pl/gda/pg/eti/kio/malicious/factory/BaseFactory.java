/*
 * Gdańsk University of Technology - Engineering Thesis
 * Malicious Module for Netbeans
 *
 * Cilińdź Michał, Gabryelska Nela, Micał Marek
 */
package pl.gda.pg.eti.kio.malicious.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

/**
 *
 * @author Michał Cilińdź
 */
public abstract class BaseFactory<T extends Annotation> {

    protected Class<T> genericClass;
    protected Map<String, Class<T>> annotatedClass;

    public BaseFactory() {
        genericClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        annotatedClass = new HashMap<>();
        scan();
    }

    private void scan() {
        Set<Class<?>> runMalicious = scanAnnotatedClass();
        for (Class clazz : runMalicious) {
            T annot = (T) clazz.getAnnotation(genericClass);
            annotatedClass.put(getAnnotationName(annot), clazz);
        }
    }

    protected Set<Class<?>> scanAnnotatedClass() {
        Reflections reflections = new Reflections(getPackageName());
        return reflections.getTypesAnnotatedWith(genericClass);
    }

    protected abstract String getPackageName();

    protected abstract String getAnnotationName(T annot);
}
