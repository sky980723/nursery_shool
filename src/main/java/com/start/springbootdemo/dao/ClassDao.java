package com.start.springbootdemo.dao;

import com.start.springbootdemo.entity.Class;
import com.start.springbootdemo.entity.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface ClassDao {
	Integer saveClass(Class studentClass);

	Integer updateClass(Class studentClass);

	Integer deleteClass(@Param("id") String id);

	Integer countByGradeName(@Param("gradeName") String gradeName, @Param("id") String id, @Param("schoolId") String schoolId);

	Integer saveGrade(Grade grade);

	Integer updateGrade(Grade grade);

	int countClass(@Param("gradeId") String gradeId, @Param("className") String className, @Param("schoolId") String schoolId,
				   @Param("id") String id);

	Integer getOrders(@Param("schoolId") String schoolId);

	Integer deleteGrade(@Param("id") String id);

	List<Grade> listGrade(@Param("schoolId") String schoolId);

	List<Class> listClass(@Param("gradeId") String gradeId);

	int countClassByNames(@Param("className") String className, @Param("gradeName") String gradeName, @Param("schoolId") String schoolId);

	String getClassId(@Param("schoolId") String schoolId,@Param("gradeName") String gradeName,@Param("className") String className);
}
