package restoran.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import restoran.model.Blagajna;
import restoran.model.Porudzbina;
import restoran.model.User;
import restoran.repository.BlagajnaRepository;
import restoran.repository.UserRepository;
import restoran.security.UserService;
import restoran.service.BlagajnaService;
import restoran.service.PorudzbinaService;



@Controller
public class AdminController {  
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlagajnaService blagajnaService;
	
	@Autowired
	private BlagajnaRepository blagajnaRepository;
	
	@Autowired
	private PorudzbinaService porudzbinaService;
	

	

        
	@GetMapping("/admin/usersSve")
	public String listaUsers(Model model) {
		model.addAttribute("users", userService.findAll());		
		return "/admin/user";
	}
	
	
	
	@RequestMapping(value = "/admin/users", method = RequestMethod.GET)
	public String sviItrazeniUsers (@RequestParam (required = false) String username,
					HttpServletRequest request, Model model) {
	    
		int page = 0;
		int size = 5;
			    
	    Page<User> userPage = null;
	
			if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
				page = Integer.parseInt(request.getParameter("page")) - 1;
			}

			if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
				size = Integer.parseInt(request.getParameter("size"));
			}
		    if (username != null) {
		    	userPage = userService.search(username, page);
		    }
		    else {
		    	userPage = userRepository.findAll(PageRequest.of(page, size));
		    }
		    
	    model.addAttribute("users", userPage);
	    return "/admin/user";
	}
	

	
	 @GetMapping("/admin/deleteuser/{id}")
	    private String deleteUser(@PathVariable("id") Integer id){
	    	User  user = userService.getOne(id);
	        if(user != null){
	        	userService.delete(id);
	        }else{
	            System.err.println("not found");
	        }
	        return "redirect:/admin/users";
	    }
	    

	    @GetMapping(path = {"/admin/edituser/{id}"})
	    private String addFormUser(@PathVariable("id") Integer id, Model model){
	        if(id != null){
	            model.addAttribute("user", userService.getOne(id) );
	        }
	        else{
	        	return null;
	        }
	        return "/admin/user-edit";
	    }
	    
	    
	    @PostMapping("/admin/editUser")
	    private String insertOrUpdateUser(@Valid User user, BindingResult bindingResult){
	        if (bindingResult.hasErrors()) {
	        	return "/admin/user-edit";
	        	} 
	        else{
	        	User editUser = userService.getOne(user.getId());
	            if(editUser !=null){
	            	editUser.setUsername(user.getUsername());
	            	editUser.setPassword(user.getPassword());
	            	userService.saveUser(editUser);
	            }
	        }
	        return "redirect:/admin/users";
	    }
	
	
	
	
    
	    @RequestMapping(value = "/admin/blagajne", method = RequestMethod.GET)
		public String sveItrazeneBlagajne(@RequestParam(value = "datum",required=false) String datum,
										@RequestParam(value = "ukupno",required=false) Double ukupno, 
										HttpServletRequest request, Model model) {
		    
	    	    	
			int page = 0;
			int size = 5;
			
		    Page<Blagajna> blagajnaPage = null;
			
		
		
				if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
					page = Integer.parseInt(request.getParameter("page")) - 1;
				}

				if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
					size = Integer.parseInt(request.getParameter("size"));
				}
				
				if( datum !="" || ukupno!=null ) {
					if(datum=="") {
						datum=null;
					}
					blagajnaPage = blagajnaService.search(datum,ukupno, page);			
			    }
				
				if(datum==null && ukupno==null || datum=="" && ukupno==null) {
			    	blagajnaPage = blagajnaRepository.findAll(PageRequest.of(page, size));
			    }
				
			    
		    model.addAttribute("blagajne", blagajnaPage);
		    return "/admin/blagajna-sve";
		}

	    
	    
	    
	    
	    
	    @GetMapping("/admin/deleteblagajna/{id}")
	    private String deleteBlagajna(@PathVariable("id") Integer id){
	    	Blagajna  blagajna = blagajnaService.getOne(id);
	        if(blagajna != null){
	        	blagajnaService.delete(id);
	        	List<Porudzbina> porudzbine = porudzbinaService.findByIdBlagajne(blagajna.getId());
	        	for(Porudzbina porudzbina: porudzbine) {
	        		porudzbinaService.delete(porudzbina.getId());
	        	}
	        }else{
	            System.err.println("not found");
	        }
	        return "redirect:/admin/blagajne";
	    }
	    
	
	    @GetMapping(path = {"/admin/editblagajna/{id}"})
	    private String addFormBlagajna(@PathVariable("id") Integer id, Model model){
	        if(id != null){
	            model.addAttribute("blagajna", blagajnaService.getOne(id) );
	        }
	        else{
	        	return null;
	        }
	        return "/admin/blagajna-edit";
	    }
	    
	    
	    @PostMapping("/admin/editBlagajna")
	    private String insertOrUpdateBlagajna(@Valid Blagajna blagajna, BindingResult bindingResult){
	        if (bindingResult.hasErrors()) {
	        	return "/admin/blagajna-edit";
	        	} 
	        else{
	        	Blagajna editBlagajna = blagajnaService.getOne(blagajna.getId());
	            if(editBlagajna !=null){
	            	editBlagajna.setDatum(blagajna.getDatum());
	            	editBlagajna.setUkupno(blagajna.getUkupno());
	            	editBlagajna.setClosed(blagajna.isClosed());
	            	blagajnaService.save(editBlagajna);
	            }
	        }
	        return "redirect:/admin/blagajne";
	    }
	
	    
	    
	    
	    
	
    
}
