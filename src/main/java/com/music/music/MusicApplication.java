package com.music.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class MusicApplication {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private MusicRepo musicRepo;

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean() {
		return (args) -> {
			if(roleRepo.findAll().size() <= 1) {
				Role music = new Role("meloman");
				Role admin = new Role("admin");
				roleRepo.save(music);
				roleRepo.save(admin);
			}
			if(userRepo.findByEmail("admin@music.com") == null) {
				User user = new User();
				user.setName("Admin");
				user.setEmail("admin@music.com");
				BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
				String pass = cryptPasswordEncoder.encode("admin");
				user.setPassword(pass);
				Role admin = roleRepo.findByName("admin");
				user.addRole(admin);
				userRepo.save(user);
			}
			if(musicRepo.findAll().size() <= 1) {
			   Music music1 = new Music("lalala", "lalala");
			   Music music2 = new Music("хабиб", "хабиб");
			   Music music3 = new Music("хабиббеги", "хабиббеги");
			   Music music4 = new Music("федерико", "федерико");
			   Music music5 = new Music("магнитофон", "магнитофон");

			   musicRepo.saveAll(List.of(music1, music2,music3, music4,music5));
			}
		};
	}
}
