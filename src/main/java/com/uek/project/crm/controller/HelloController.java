package com.uek.project.crm.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.uek.project.crm.dao.IProductDao;
import com.uek.project.crm.entity.Product;
import com.uek.project.crm.service.impl.ProductServiceImpl;
import com.uek.project.crm.service.prototype.IProductService;
//import com.uek.project.crm.service.prototype.IProductService;

/**
 * Hello Spring Boot
 * 
 * @author l
 *
 */
@Controller
public class HelloController {
	@Autowired
	private IProductDao productDao;

	@Autowired
	private IProductService prodService;

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "hello spring boot";
	}

	@GetMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		if (session.getAttribute("page") == null) {
			session.setAttribute("page", 1);
		}
		int page = (int) session.getAttribute("page");
		if (page < 1) {
			page = 1;
			session.setAttribute("page", page);
		}
		Page<Product> p = null;
		int total = 0;
		if (session.getAttribute("total") != null) {
			total = (int) session.getAttribute("total");
			if (page >= total) {
				page = total;
				session.setAttribute("page", page);
			}
		}
		p = PageHelper.startPage(page, 5);
		mv.addObject("products", productDao.findAll());
		total = p.getPages();
		session.setAttribute("total", total);
		Integer[] totalNums = new Integer[total];
		for (int i = 0; i < total; i++) {
			totalNums[i] = i + 1;
		}
		mv.addObject("total", totalNums);
		mv.addObject("current", page);
		mv.setViewName("index");
		return mv;

	}

	@RequestMapping(value = "/add_product", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String reg(Product product) {

		productDao.save(product);
		return "{\"0\":\"注册成功,请查看数据库。\"}";

	}

	@RequestMapping(value = "/delete_product", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String sdf(int id) {
		productDao.delete(id);
		return "{\"0\":\"注册成功,请查看数据库。\"}";

	}

	@RequestMapping(value = "/update_product", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String safasf(Product product) {
		productDao.update(product);
		return "{\"0\":\"注册成功,请查看数据库。\"}";

	}

	@GetMapping("/demo")
	public String demo() {
		return "demo";
	}

	@RequestMapping(value = "/setpage", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String sg(int pageOffset, HttpSession session) {
		session.setAttribute("page", (int) session.getAttribute("page") + pageOffset);
		return "{\"0\":\"下单成功,请查看数据库。\"}";
	}

	@RequestMapping(value = "/setactualpage", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String strd(int pages, HttpSession session) {
		session.setAttribute("page", pages);
		return "{\"0\":\"下单成功,请查看数据库。\"}";
	}

	@GetMapping("toupload")
	public ModelAndView toUpload() {
		ModelAndView mv = new ModelAndView();
		// -- 获取所有的分类
		File allCat = new File("F:/upload/");
		String[] ac = allCat.list();
		mv.addObject("categories", ac);
		mv.setViewName("upload");
		return mv;
	
	}

	@PostMapping("/upload")
	@ResponseBody
	public String uploadFile(String apple,@RequestParam("file") MultipartFile[] files) throws Exception {
		System.out.println(files);
		for (MultipartFile file : files) {
			File f = new File("F:/upload/"+apple+"/"+ file.getOriginalFilename());
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			file.transferTo(f);
		}
		return "{'upload':'ok'}";
	}

	@GetMapping("list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		// -- 获取所有的分类
		File allCat = new File("F:/upload/");
		String[] ac = allCat.list();
		mv.addObject("categories", ac);
		mv.setViewName("list");
		return mv;
	}

	@GetMapping("/list/{category}")
	@ResponseBody
	public String list(@PathVariable String category) {
		// -- 1.拿到该目录下的所有文件
		File cf = new File("F:/upload/" + category);
		String[] files = cf.list();
		// -- 2. 返回文件列表JSON字符串
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("files", files);
		return jsonobj.toJSONString();
	}

	@GetMapping("/download/{category}/{file}")
	public void download(@PathVariable("category") String category, @PathVariable("file") String file,
			HttpServletResponse response) throws Exception {
		File f = new File("f:/upload/" + category + "/" + file);
		//-- 设置响应类型
		response.setContentType("application/force-dowmload");
		//-- 设置响应文件名
		response.addHeader("Content-Disposition", "attachment;fileName="+f.getName());
		// -- 文件输入流对象
		FileInputStream fis = new FileInputStream(f);
		// -- 文件缓存流对象
		BufferedInputStream bis = new BufferedInputStream(fis);
		// -- 文件输出流对象
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = bis.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		bis.close();
		fis.close();

	}
}