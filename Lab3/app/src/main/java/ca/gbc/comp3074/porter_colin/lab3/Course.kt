package ca.gbc.comp3074.porter_colin.lab3

import android.content.Context

data class Course(
    val code: String,
    val name: String
)

fun loadCourses(context: Context):List<Course>{
    val codes = context.resources.getStringArray(R.array.course_codes)
    val names = context.resources.getStringArray(R.array.course_names)
    return codes.zip(names){
        code, name ->
        Course(code = code, name = name)
    }
}