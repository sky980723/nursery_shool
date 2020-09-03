package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sky
 */
@Repository
public interface StudentDao {
	List<Student> listStudent(@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize,
							  @Param("classId") String classId, @Param("schoolId") String schoolId, @Param("name") String name,
							  @Param("openId") String openId);

	Patriarch getPatriarch(@Param("mobile") String mobile, @Param("schoolId") String schoolId);

	Integer savePatriarch(Patriarch patriarch);

	Integer updatePatriarch(Patriarch patriarch);

	Integer saveStudent(Student student);

	Integer savePatriarchStudent(PatriarchStudent patriarchStudent);

	Integer countStudent(@Param("mobile") String mobile, @Param("studentName") String studentName,
						 @Param("schoolId") String schoolId);

	Integer saveStudentApply(StudentApply studentApply);

	Integer updateStuentApply(StudentApply studentApply);

	StudentLikeRecord saveOrUpdateLike(@Param("studentId") String studentId, @Param("openId") String openId);

	Integer saveLikeReocrd(StudentLikeRecord studentLikeRecord);

	Integer updateLikeRecord(StudentLikeRecord studentLikeRecord);

	List<Student> getStudent(@Param("openId") String openId,@Param("schoolId") String schoolId);

	List<StudentImg> listStudentImg(@Param("studentId") String studentId);

	Integer saveStudentImg(StudentImg studentImg);

	Integer updateStudentImg(StudentImg studentImg);

	Integer deleteStudentImg(@Param("id") String id);

	Integer updateStudent(Student student);

	int countStudenByClassName(@Param("schoolId") String schoolId, @Param("className") String className, @Param("studentName") String studentName);
}
