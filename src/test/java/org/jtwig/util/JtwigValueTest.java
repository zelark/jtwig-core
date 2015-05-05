package org.jtwig.util;

import org.jtwig.value.JtwigValue;
import org.jtwig.value.JtwigValueFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

public class JtwigValueTest {
    private JtwigValue underTest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asCollectionWhenNull() throws Exception {
        underTest = JtwigValueFactory.create(null);
        Collection<Object> result = underTest.asCollection();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asCollectionWhenIterable() throws Exception {
        List<String> list = asList("one");

        underTest = JtwigValueFactory.create((Iterable) list);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("one"));
    }

    @Test
    public void asCollectionWhenArray() throws Exception {
        underTest = JtwigValueFactory.create(new String[]{"test"});
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("test"));
    }

    @Test
    public void asCollectionWhenMap() throws Exception {
        underTest = JtwigValueFactory.create(new HashMap<Object, Object>() {{
            put("a", "b");
        }});
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem("b"));
    }

    @Test
    public void asCollectionWhenSingleValue() throws Exception {
        underTest = JtwigValueFactory.create(1);
        Collection<Object> result = underTest.asCollection();

        assertThat(result, hasItem(1));
    }

    @Test
    public void asMapWhenNull() throws Exception {
        underTest = JtwigValueFactory.create(null);
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(true));
    }

    @Test
    public void asMapWhenSingleValue() throws Exception {
        underTest = JtwigValueFactory.create("ola");
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0), is((Object)"ola"));
    }

    @Test
    public void mandatoryNumberInvalid() throws Exception {
        expectedException.expect(IllegalArgumentException.class);

        underTest = JtwigValueFactory.create("a");
        underTest.mandatoryNumber();
    }

    @Test
    public void asMapWhenList() throws Exception {
        underTest = JtwigValueFactory.create(asList("one"));
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) "one"));
    }

    @Test
    public void isPresentWhenNull() throws Exception {
        underTest = JtwigValueFactory.create(null);
        boolean result = underTest.isPresent();
        assertThat(result, is(false));
    }

    @Test
    public void isPresentWhenNonNull() throws Exception {
        underTest = JtwigValueFactory.create(1);
        boolean result = underTest.isPresent();
        assertThat(result, is(true));
    }

    @Test
    public void asMapWhenArray() throws Exception {
        underTest = JtwigValueFactory.create(new Integer[]{1,2});
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get(0), is((Object) 1));
        assertThat(result.get(1), is((Object) 2));
    }

    @Test
    public void asMapWhenMap() throws Exception {
        underTest = JtwigValueFactory.create(new HashMap<Object, Object>() {{ put("a", "b"); }});
        Map<Object, Object> result = underTest.asMap();

        assertThat(result.get("a"), is((Object) "b"));
    }
}