package com.start.springbootdemo.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.start.springbootdemo.dao.ClassDao;
import com.start.springbootdemo.dao.StudentDao;
import com.start.springbootdemo.entity.*;
import com.start.springbootdemo.service.IStudentService;
import com.start.springbootdemo.util.*;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Sky
 */
@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private ClassDao classDao;

	@Override
	public Results<List<Student>> listStudent(Integer page, String name, String classId, String schoolId, String openId) {
		Results<List<Student>> results = new Results<>();
		int pageSize = Patterns.pageSize;
		int pageNo = (page - 1) * pageSize;
		List<Student> list = studentDao.listStudent(pageNo, pageSize, classId, schoolId, name, openId);
		results.setStatus("0");
		results.setData(list);

		return results;
	}

	/**
	 * 添加家长信息，与学生生成绑定关系
	 *
	 * @param patriarch
	 * @return
	 */
	@Transactional
	@Override
	public Results<String> savePatriarch(Patriarch patriarch) {
		Results<String> results = new Results<>();
		//先创建家长的基本信息
		//根据手机号和schoolId查询是否存在对象，以此判断是添加还是修改
		Patriarch oldPatriarch = studentDao.getPatriarch(patriarch.getMobile(), patriarch.getSchoolId());
		String patriarchId;
		Date date = new Date();
		if (oldPatriarch == null) {
			//先添加一个家长的基本信息
			patriarchId = KeyGen.uuid();
			patriarch.setId(patriarchId);
			patriarch.setAddtime(date);
			studentDao.savePatriarch(patriarch);
		} else {
			//更新一下家长的基本信息
			patriarchId = patriarch.getId();
			patriarch.setUpdatetime(date);
			studentDao.updatePatriarch(patriarch);
		}
		//如果有学生对象，先去添加学生对象
		String studentId = KeyGen.uuid();
		if (patriarch.getStudent() != null) {
			//添加一个学生
			Student student = patriarch.getStudent();
			student.setId(studentId);
			student.setSchoolId(patriarch.getSchoolId());
			student.setAddtime(date);
			studentDao.saveStudent(student);
		} else {
			studentId = patriarch.getStudentId();
		}
		//创建关系
		PatriarchStudent patriarchStudent = new PatriarchStudent();
		patriarchStudent.setId(KeyGen.uuid());
		patriarchStudent.setPatriarchId(patriarchId);
		patriarchStudent.setStudentId(studentId);
		patriarchStudent.setRelation(patriarch.getRelation());
		patriarchStudent.setAddtime(date);
		studentDao.savePatriarchStudent(patriarchStudent);

		results.setStatus("0");

		return results;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Results<String> saveOrUpdateStudentApply(StudentApply studentApply) {
		Results<String> results = new Results<>();
		Date date = new Date();
		//根据id是否存在判断是添加还是修改
		if (StringUtils.isEmpty(studentApply.getId())) {
			//是添加
			//根据手机号和孩子姓名查询记录，如果存在就不要再次录入了
			int count = studentDao.countStudent(studentApply.getMobile(), studentApply.getStudentName(),
					studentApply.getSchoolId());
			if (count != 0) {
				results.setStatus("1");
				results.setMessage("该宝贝已报名过,请勿重复提交表单~");

				return results;
			}
			studentApply.setId(KeyGen.uuid());
			studentApply.setAddtime(date);
			studentDao.saveStudentApply(studentApply);
		} else {
			//是修改
			studentApply.setUpdatetime(date);
			studentDao.updateStuentApply(studentApply);
		}
		results.setStatus("0");

		return results;
	}

	@Override
	public Results<String> saveOrUpdateLike(String studentId, String openId) {
		Results<String> results = new Results<>();
		//根据studentid 和 openID查询点赞记录表中是否存在记录
		Date date = new Date();
		StudentLikeRecord
				studentLikeRecord = studentDao.saveOrUpdateLike(studentId, openId);
		if (studentLikeRecord == null) {
			StudentLikeRecord slr = new StudentLikeRecord();
			//准备点赞,生成一条点赞记录
			slr.setId(KeyGen.uuid());
			slr.setStudentId(studentId);
			slr.setOpenId(openId);
			slr.setIsshow(1);
			slr.setAddtime(date);
			studentDao.saveLikeReocrd(slr);
		} else {
			//修改isshow字段，取反
			if (studentLikeRecord.getIsshow() == 1) {
				studentLikeRecord.setIsshow(0);
			} else {
				studentLikeRecord.setIsshow(1);
			}
			studentLikeRecord.setUpdatetime(date);
			studentDao.updateLikeRecord(studentLikeRecord);
		}
		results.setStatus("0");

		return results;
	}

	@Override
	public Results<String> getOpenId(String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
		Results<String> results = new Results<>();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		Map<String, String> mapss = new HashMap<String, String>();
		mapss.put("appid", "");
		mapss.put("secret", "");
		mapss.put("code", code);
		mapss.put("grant_type", "authorization_code");

		Results<byte[]> rbody = Requests.get("https://api.weixin.qq.com/sns/oauth2/access_token", null, mapss);
		byte[] bytess = rbody.getData();
		String body = new String(bytess);
		AccessToken tokens = Json.from(body, AccessToken.class);
		if (tokens != null) {
			Map<String, String> map = new HashMap<>();
			map.put("access_token", tokens.getAccess_token());
			map.put("openid", tokens.getOpenid());
			map.put("lang", "zh_CN");
			Results<byte[]> userinfobody = Requests.get("https://api.weixin.qq.com/sns/userinfo", null, map);
			byte[] byteuser = userinfobody.getData();
			String bodyuser = new String(byteuser);
			WeixinInfo userInfo = Json.from(bodyuser, WeixinInfo.class);
			if (userInfo != null) {

				// 这个地方是跳转链接
				response.sendRedirect("http://kz.zhongshicc.com/web/outstandingStudents/index?openId=" + userInfo.getOpenid());
			}

		}
		return null;
	}

	@Override
	public Results<Map<String, Object>> getStudent(String openId) {
		Results<Map<String, Object>> results = new Results<>();
		Map<String, Object> map = new HashMap<>();
		//获取绑定的学生对象
		Student student = studentDao.getStudent(openId);
		map.put("student", student);
		//获取相册集合
		if (student != null) {
			List<StudentImg> list = studentDao.listStudentImg(student.getId());
			map.put("imgList", list);
		}
		results.setStatus("0");
		results.setData(map);

		return results;
	}

	@Override
	public Results<String> getCode(HttpServletResponse response) throws IOException {

		Results<Map<String, String>> result = new Results<>();

		String state = null;
		response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + "这个地方拼接appid"
				+ "&redirect_uri=http%3a%2f%2ftiku.sdymei.com%2fapi%2fwx%2fweb%2f " +
				"newbank&response_type=code&scope=snsapi_base&state="
				+ state + "#wechat_redirect");
		return null;
	}

	@Override
	public Results<String> saveOrUpdateStudentImg(StudentImg studentImg) {
		Results<String> results = new Results<>();
		Date date = new Date();
		//根据id判断是添加还是修改
		if (StringUtils.isEmpty(studentImg.getId())) {
			//是添加
			studentImg.setId(KeyGen.uuid());
			studentImg.setAddtime(date);
			studentDao.saveStudentImg(studentImg);
		} else {
			//是修改
			studentImg.setUpdatetime(date);
			studentDao.updateStudentImg(studentImg);

		}
		results.setStatus("0");

		return results;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Results<String> saveOrUpdateStudent(Student student) {
		Results<String> results = new Results<>();
		Date date = new Date();
		//根据id判断是添加还是修改
		if (StringUtils.isEmpty(student.getId())) {
			//添加
			student.setId(KeyGen.uuid());
			student.setAddtime(date);
			studentDao.saveStudent(student);
		} else {
			//修改
			student.setUpdatetime(date);
			studentDao.updateStudent(student);
		}
		results.setStatus("0");

		return results;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Results<String> deleteStudentImg(String id) {
		Results<String> results = new Results<>();
		//根据id删除一个相册
		studentDao.deleteStudentImg(id);
		results.setStatus("0");

		return results;
	}

	@Override
	public Results<String> improtExcel(MultipartFile file, HttpServletRequest request) {
		Results<String> results = new Results<>();
		//获取schoolid
		String schoolId = String.valueOf(request.getSession().getAttribute("schoolId"));
		if (StringUtils.isEmpty(schoolId)) {
			results.setStatus("1");
			results.setMessage("登录超时，请重新登录~");

			return results;
		}
		//获取excel的内容
		Workbook wb;
		try {
			wb = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			int rowNumber = sheet.getPhysicalNumberOfRows() - 1;
			// test
			Iterator<Row> rowIterator = sheet.rowIterator();
			Row titleRow = rowIterator.next();
			titleRow.getLastCellNum();
			Date date = new Date();
			List<Student> list = new ArrayList<>();
			for (int i = 1; i <= rowNumber && rowIterator.hasNext(); i++) {
				Row row = sheet.getRow(i);
				//验证班级名称、年级名称是否合法
				if (!verifyClassNameOrGradeName(schoolId, checkNull(0, row), null)) {
					continue;
				}
				if (!verifyClassNameOrGradeName(schoolId, checkNull(0, row), checkNull(1, row))) {
					continue;
				}
				//根据学生姓名和班级名称查询是否存在
				if (!countStudent(schoolId,checkNull(1,row),checkNull(2,row))) {
					continue;
				}
				//获取所在班级的ID
				String classId = classDao.getClassId(schoolId,checkNull(0,row),checkNull(1,row));
				// 这个new对象必须放在循环内部
				Student student = new Student();
				student.setId(KeyGen.uuid());
				student.setName(checkNull(2,row));
				student.setSex(checkNull(3,row));
				student.setAttendSchoolTime(checkNull(4,row));
				student.setAddtime(date);
				list.add(student);
			}
			// 调个方法去个重复
			//list = removeDuplicateUser(list);
			// 数据总量
			int rowSize = list.size();
			// 每个线程处理的数量
			int count = 100;
			// 开启的线程数
			int runSize = (rowNumber / count) + 1;
			// 创建一个线程池 线程数和开启线程一致
			ExecutorService executor = Executors.newFixedThreadPool(runSize);
			// 创建两个计数器
			CountDownLatch begin = new CountDownLatch(1);
			CountDownLatch end = new CountDownLatch(runSize);
			// 为每个线程分配需要执行的数据
			for (int i = 0; i < runSize; i++) {
				int startIdx = 0;
				int endIdx = 0;
				if ((i + 1) == runSize) {
					startIdx = (i * count);
					endIdx = rowSize;
				} else {
					startIdx = (i * count);
					endIdx = (i + 1) * count;
				}
				// 调取封装的线程方法
				TestBatchInsertThread thread = new TestBatchInsertThread(list, begin, end, startIdx, endIdx);
				executor.execute(thread);
			}
			begin.countDown();
			end.await();
			// 停止线程
			executor.shutdown();
			// 计时结束
			return results;
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int ExcelDoing(List<Student> list, int sta, int end) {
		list = list.subList(sta, end);
		studentDao.insertStudentList(list);

		return 0;
	}

	/**
	 * 验证班级、年级名称是否合法
	 *
	 * @param schoolId
	 * @param gradeName
	 * @param className
	 * @return
	 */
	public boolean verifyClassNameOrGradeName(String schoolId, String gradeName, String className) {
		//分情况验证
		int count = 0;
		if (StringUtils.isEmpty(gradeName)) {
			//两个都为空
			return false;
		} else if (StringUtils.isEmpty(className) && StringUtils.isNotEmpty(gradeName)) {
			//验证年级名称是否合法
			count = classDao.countByGradeName(gradeName, null, schoolId);
			if (count == 0) {
				return true;
			}
		} else if (StringUtils.isNotEmpty(className) && StringUtils.isNotEmpty(gradeName)) {
			//验证班级名称是否合法
			count = classDao.countClassByNames(className, gradeName, schoolId);
			if (count == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 查询学生是否存在
	 *
	 * @param schoolId
	 * @param className
	 * @param studentName
	 * @return
	 */
	public boolean countStudent(String schoolId, String className, String studentName) {
		if (StringUtils.isEmpty(studentName) || StringUtils.isEmpty(className)) {
			return false;
		}
		int count = studentDao.countStudenByClassName(schoolId, className, studentName);
		if (count == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 处理单元格为空的问题
	 *
	 * @param i
	 * @param row
	 * @return
	 */
	public static String checkNull(int i, Row row) {
		Cell cell = row.getCell(i);
		if (cell != null) {
			cell.setCellType(CellType.STRING);
			if (cell.getStringCellValue() != null && !"".equals(cell.getStringCellValue())) {
				return cell.getStringCellValue().trim();
			}
		}

		return null;
	}

	/**
	 * 集合去重复
	 *
	 * @param studentList
	 * @return
	 */
	private static ArrayList<Student> removeDuplicateUser(List<Student> studentList) {
		Set<Student> set = new TreeSet<Student>(new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				// 字符串,则按照asicc码升序排列
				return o1.getName().compareTo(o2.getName());
			}

		});
		set.addAll(studentList);
		return new ArrayList<Student>(set);
	}
}


