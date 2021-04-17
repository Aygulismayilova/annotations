
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import java.lang.reflect.Modifier;

@SupportedAnnotationTypes("*")
public class InstanceVariablePreProcessors extends AbstractProcessor {


    public static <T> void checkIsInstanceVariable(Class<T> clazz) {
        try {
            System.out.println(clazz.isAnnotationPresent(IsInstanceVariable.class));
            if (clazz.isAnnotationPresent(IsInstanceVariable.class)) {

                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    if (getAnnotation(field, IsInstanceVariable.class) != null
                            && Modifier.isStatic(field.getModifiers())) {
                        throw new IllegalArgumentException("field " + field.getName() + " is not instance");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T getAnnotation(Field field, Class clazz) {
        Annotation[] anns = field.getAnnotationsByType(clazz);
        if (anns != null && anns.length > 0) {
            return (T) anns[0];
        }
        return null;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(IsInstanceVariable.class);
        for (Element element : elements) {
            Set< javax.lang.model.element.Modifier
> modifiers = element.getModifiers();
            for (javax.lang.model.element.Modifier modifier : modifiers) {
                if (modifier == javax.lang.model.element.Modifier.STATIC) {

                    return false;
                    /*
                    throw new IllegalArgumentException("wrong argument");
*/
                }
            }
        }


      /*  System.out.println("myprocessor started");
        System.out.println(annotations);*/
/*Class[] allClasses = getClasses("src");
        for (int i = 0; i < allClasses.length; i++) {
        checkIsInstanceVariable(allClasses[i]);
        }*/
return true;
    }
}
