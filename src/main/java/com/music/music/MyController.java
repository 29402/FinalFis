package com.music.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MyController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private MusicRepo musicRepo;

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", new Message());
        return "first";
    }

    @PostMapping("/mess")
    public String message(Message message) {
        messageRepo.save(message);

        return "redirect:/";
    }

    @GetMapping("/chords")
    public String chords() {
        return "chords";
    }

    @GetMapping("/myplay")
    public String myplay(Model model) {
        MUserDetails principal = (MUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Set<Music> musics = principal.get().getMusics();
        model.addAttribute("melodias", musics.toArray());
        model.addAttribute("isEmpty", musics.size() <= 0);
        return "myplay";
    }

    @GetMapping("/review")
    public String review(Model model) {
        model.addAttribute("melodias", musicRepo.findAll().toArray());
        return "review";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String regUser(User user) {
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        String pass = cryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(pass);

        Role role = roleRepo.findByName("meloman");
        user.addRole(role);

        userRepo.save(user);
        return "first";
    }

    @PostMapping("/music/add/{id}")
    public String addMusic(@PathVariable(name = "id") Long id) {
        Music music = musicRepo.findById(id).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MUserDetails me = (MUserDetails) authentication.getPrincipal();

        User user= me.get();
        Set<Music> musics = user.getMusics();
        musics.add(music);
        user.setMusics(new HashSet<>());
        userRepo.save(user);
        user.setMusics(musics);
        userRepo.save(user);

        return "redirect:/myplay";
    }
}
