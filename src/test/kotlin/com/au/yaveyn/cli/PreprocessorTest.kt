package com.au.yaveyn.cli

import org.junit.Test
import org.junit.Assert.*

class PreprocessorTest {

    @Test
    fun testId() {
        val st = State()
        val p = Preprocessor(st)
        assertEquals("some test n&* mom's spagetti **'''''''", p.preprocess("some test n&* mom's spagetti **'''''''"))
    }

    @Test
    fun testSimpleVar() {
        val st = State()
        val p = Preprocessor(st)
        st.setVar("a", "var_a")
        st.setVar("b", "var_   b@@!!")
        assertEquals("var_a", p.preprocess("\$a"))
        assertEquals("var_   b@@!!", p.preprocess("\$b"))
    }

    @Test
    fun testVarEndings() {
        val st = State()
        val p = Preprocessor(st)
        st.setVar("a", "var_a")
        st.setVar("b", "var_   b@@!!")
        assertEquals("var_   b@@!! var_a'", p.preprocess("\$b\$ba \$a'"))
    }

}