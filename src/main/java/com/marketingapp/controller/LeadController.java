package com.marketingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.marketingapp.entities.Lead;
import com.marketingapp.services.LeadService;
import com.marketingapp.util.EmailService;
import com.marketingapp.util.EmailServiceImpl;

@Controller
public class LeadController {
	
	@Autowired
	private EmailService emailService;
	
    @Autowired
    private LeadService leadService;
    
	@RequestMapping("/viewLeadPage")
	public String viewCreateLeadPage() {
		return "create_lead";
	}
	@RequestMapping(value = "/saveLead", method = RequestMethod.POST)
	public String saveLead(@ModelAttribute("lead") Lead l, ModelMap model) {
		leadService.saveOneLead(l);
		emailService.sendSimpleMail(l.getEmail(), "Test", "Thank you for visiting our showroom");
		model.addAttribute("msg","Lead is saved");
		return "create_lead";
	}
	
	@RequestMapping("/listall")
	public String listAllLeads(ModelMap model) {
		List<Lead> leads = leadService.listAllLeads();
		model.addAttribute("leads", leads);
	    return "list_leads";
	}
	@RequestMapping("/deleteLead")
	public String deleteLead(@RequestParam("id") long id, ModelMap model) {
		leadService.deleteOneLead(id);
		List<Lead> leads = leadService.listAllLeads();
		model.addAttribute("leads", leads);
	    return "list_leads";
	}
	@RequestMapping("/updateLead")
	public String updateLead(@RequestParam("id") long id,ModelMap model) {
		Lead lead = leadService.getById(id);
		model.addAttribute("lead", lead);
		return "update_lead";
		
	}
	@RequestMapping(value = "/updateOneLead", method = RequestMethod.POST)
	public String updateOneLead(@ModelAttribute("lead") Lead l, ModelMap model) {
		leadService.saveOneLead(l);
		List<Lead> leads = leadService.listAllLeads();
		model.addAttribute("leads", leads);
	    return "list_leads";
}
}