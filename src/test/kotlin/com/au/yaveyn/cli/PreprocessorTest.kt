package com.au.yaveyn.cli

import org.junit.Test
import org.junit.Assert.*

class PreprocessorTest {

    @Test
    fun testId() {
        val st = State()
        val p = Preprocessor(st)
        assertEquals(p.preprocess("some test n&* ioahs **'''''''"), "some test n&* ioahs **'''''''")
    }

    @Test
    fun testSimpleVar() {
        val st = State()
        val p = Preprocessor(st)
        st.setVar("a", "var_a")
        st.setVar("b", "var_   b@@!!")
        assertEquals(p.preprocess("\$a"), "var_a")
        assertEquals(p.preprocess("\$b"), "var_   b@@!!")
    }

    @Test
    fun testVarEndings() {
        val st = State()
        val p = Preprocessor(st)
        st.setVar("a", "var_a")
        st.setVar("b", "var_   b@@!!")
        assertEquals(p.preprocess("\$b\$ba \$a'"), "var_   b@@!! var_a'")
    }

}