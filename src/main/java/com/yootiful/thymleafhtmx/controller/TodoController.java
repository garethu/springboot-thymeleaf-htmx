package com.yootiful.thymleafhtmx.controller;

import com.yootiful.thymleafhtmx.model.TodoItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoController {
  private static List<TodoItem> items = new ArrayList();
  static {
    TodoItem todo = new TodoItem(0,"Make the bed");
    items.add(todo);
    todo = new TodoItem(1,"Buy a new hat");
    items.add(todo);
    todo = new TodoItem(2,"Listen to the birds singing");
    items.add(todo);
  }
  public TodoController(){ }
  @GetMapping("/")
  public String items(Model model) {
    System.out.println("BEGIN items");
    model.addAttribute("itemList", items);
    return "index";
  }
  @PostMapping("/todos/{id}/complete")
//  @ResponseBody
  public String completeTodo(@PathVariable Integer id, Model model) {
    TodoItem item = null;
    for (TodoItem existingItem : items) {
      if (existingItem.getId().equals(id)) {
        item = existingItem;
        break;
      }
    }
    if (item != null) {
      item.setCompleted(!item.isCompleted());
    }
    model.addAttribute("item",item);
    return "todo";
  }
  @PostMapping("/todos")
//  @ResponseBody
  public String createTodo(Model model, @ModelAttribute TodoItem newTodo) {
    int nextId = items.stream().mapToInt(TodoItem::getId).max().orElse(0) + 1;
    newTodo.setId(nextId);
    System.out.println("newTodo: " + newTodo);
    items.add(newTodo);
//    return "<li>"+newTodo.getDescription()+"<input type='checkbox' th:checked='${item.isCompleted}' hx-trigger='click' th:hx-post='|/todos/${item.id}/complete|'></li>";
    model.addAttribute("item", newTodo);
    return "todo";
  }
  @PostMapping("/todos/{id}/delete")
  @ResponseBody
  public String deleteTodo(@PathVariable Integer id) {
    for (int i = 0;  i < items.size(); i++) {
      TodoItem item = items.get(i);
      if (item.getId() == id) {
        items.remove(i);
        break;
      }
    }
    return "";
  }

}

