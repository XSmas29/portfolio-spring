package id.my.bumantara.portfoliospring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
  @GetMapping("")
  public String home() {
    return "Welcome";
  }
}
