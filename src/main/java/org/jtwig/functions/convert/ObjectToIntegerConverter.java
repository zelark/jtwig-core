package org.jtwig.functions.convert;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.jtwig.reflection.convert.Converter;
import org.jtwig.reflection.model.Value;
import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;

import java.math.BigDecimal;

public class ObjectToIntegerConverter implements Converter {

    @Override
    public Optional<Value> convert(Object value, Class aClass) {
        if (aClass.equals(Integer.class) || aClass.equals(Integer.TYPE)) {
            Optional<BigDecimal> optional = JtwigValueFactory.create(value).asNumber();
            return optional.transform(new Function<BigDecimal, Value>() {
                @Override
                public Value apply(BigDecimal input) {
                    return new Value(input.intValue());
                }
            });
        }
        return Optional.absent();
    }
}
