package com.my_app.data.utils

import com.my_app.data.utils.GsonUtils.toClassValue
import com.my_app.data.utils.GsonUtils.toStringValue
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Test

class GsonUtilsTest {

    @Test
    fun `test toClassValue converts json string to object successfully`() {
        val jsonString = """{"name":"John","age":30}"""
        val person: Person? = jsonString.toClassValue()

        assertNotNull(person)
        assertEquals("John", person?.name)
        assertEquals(30, person?.age)
    }

    @Test
    fun `test toStringValue converts object to json string successfully`() {
        val person = Person("John", 30)
        val jsonString: String = person.toStringValue()

        assertNotNull(jsonString)
        assertTrue(jsonString.contains("John"))
        assertTrue(jsonString.contains("30"))
    }

    @Test
    fun `test toClassValue returns null when input is null`() {
        val result: Person? = null.toClassValue()
        assertNull(result)
    }

    @Test
    fun `test toStringValue returns valid json for empty object`() {
        val person = Person("", 0)
        val jsonString: String = person.toStringValue()

        assertNotNull(jsonString)
        assertTrue(jsonString.contains("\"name\":\"\""))
        assertTrue(jsonString.contains("\"age\":0"))
    }
}

data class Person(val name: String, val age: Int)
