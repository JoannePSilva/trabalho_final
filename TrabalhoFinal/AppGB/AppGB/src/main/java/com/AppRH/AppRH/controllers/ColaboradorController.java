package com.AppRH.AppRH.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Colaborador;
import com.AppRH.AppRH.repository.ColaboradorRepository;

@Controller
public class ColaboradorController {

	@Autowired
	private ColaboradorRepository cr;

	// GET que chama o form para cadastrar funcionários
	@RequestMapping("cadastrar_colaborador ")
	public String form() {
		return "colaborador/form-colaborador";
	}

	// POST que cadastra funcionários
	@RequestMapping(value = "/cadastrar_colaborador ", method = RequestMethod.POST)
	public String form(@Valid Colaborador funcionario, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrar_colaborador ";
		}

		cr.save(funcionario);
		attributes.addFlashAttribute("mensagem", "Colaborador cadastrado com sucesso!");
		return "redirect:/cadastrar_colaborador ";
	}

	// GET que lista funcionários
	@RequestMapping("/colaboradores")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("colaborador/lista-colaborador");
		Iterable<Colaborador> colaboradores = cr.findAll();
		mv.addObject("colaboradores", colaboradores);
		return mv;
	}

	// GET que lista dependentes e detalhes dos funcionário
	@RequestMapping("/detalhes-colaborador/{id}")
	public ModelAndView detalhesColaborador(@PathVariable("id") long id) {
		Colaborador colaborador = cr.findById(id);
		ModelAndView mv = new ModelAndView("colaborador/detalhes-colaborador");
		mv.addObject("colaborador", colaborador);

		return mv;

	}

	// GET que deleta funcionário
	@RequestMapping("/deletarColaborador")
	public String deletarFuncionario(long id) {
		Colaborador colaborador = cr.findById(id);
		cr.delete(colaborador);
		return "redirect:/colaboradores";

	}

	// Métodos que atualizam funcionário
	// GET que chama o FORM de edição do funcionário
	@RequestMapping("/editar-colaborador")
	public ModelAndView editarFuncionario(long id) {
		Colaborador colaborador = cr.findById(id);
		ModelAndView mv = new ModelAndView("colaborador/update-colaborador");
		mv.addObject("colaborador", colaborador);
		return mv;
	}

	// POST que atualiza o funcionário
	@RequestMapping(value = "/editar-colaborador", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Colaborador colaborador, BindingResult result,
			RedirectAttributes attributes) {

		cr.save(colaborador);
		attributes.addFlashAttribute("success", "Colaborador alterado com sucesso!");

		long idLong = colaborador.getId();
		String id = "" + idLong;
		return "redirect:/detalhes-colaborador/" + id;

	}

}
