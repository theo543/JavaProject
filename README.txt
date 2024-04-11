Queries:
- Add student: Student must have a name, a birthdate, emergency contact, optional grant with money amount
- Add teacher: Teacher must have a name, a birthdate, a salary
- Add course: Course must have a teacher and a classroom.
- Assign teacher to course
- Add exam to course
- Add grade from 1 to 10 for student in course
- Add special 0/10 fail for student in course, due to misconduct (cheating, absence, etc.)
- Query student report card
- Query exam statistics
- Query course statistics

Model classes:
- Person
-- Student
-- Teacher
- Course
- Exam
- Grade
-- GeneralGrade
-- FailGrade
- Classroom
- Grant
