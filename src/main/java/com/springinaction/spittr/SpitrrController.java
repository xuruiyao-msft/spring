package com.springinaction.spittr;

import com.springinaction.exceptions.SpittrNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/spittles")
public class SpitrrController {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpitrrController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String spittles(@RequestParam(value = "max", defaultValue = Long.MAX_VALUE + "") Long max,
                           @RequestParam(value = "count", defaultValue = "20") int count) {
//        return spittleRepository.findSpittles(max, count);
        return "home";
    }

    @RequestMapping(value = "/find/{spittleId}", method = RequestMethod.GET)
    public String spittle(@PathVariable("spittleId") long spittleId, Model model) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute(new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submit(@RequestPart("picture") MultipartFile picture, @Valid User user, Errors errors) throws IOException {
        if (errors.hasErrors()) {
            return "registration";
        }
        picture.transferTo(new File("/tmp/" + picture.getOriginalFilename()));
        return "redirect:/spittles/" + user.getUsername();
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public String showInfo(@PathVariable String username, Model model) {
        model.addAttribute("spittle", spittleRepository.findByUserName(username));
        if (true) {
            throw new SpittrNotFound();
        }
        return "showinfo";
    }

}
