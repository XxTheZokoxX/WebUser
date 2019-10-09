import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../usuario.service';
import { User } from 'src/model/user';
import { UsuarioRestService } from '../usuario-rest.service';

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  modoEdicion: boolean;
  userSelected: User;
  modoBorrar: boolean;
  users: User[];

  userInsertado: User;
  name: string;
  age: string;
  email: string;
  password: string;
  constructor(private userService: UsuarioService, private userRestService: UsuarioRestService) { }

  ngOnInit() {
    this.getUsuarios();
    this.modoEdicion = false;
    this.modoBorrar = false;
  }
  getUsuarios() {
    this.userService.getUsuarios().subscribe(user => this.users = user);
  }
  onSelect(user: User) {
    this.userSelected = user;

  }
  enableEdition() {
    this.modoEdicion = !this.modoEdicion;
  }
  enableDeleteById() {
    this.modoBorrar = !this.modoBorrar;
  }
  
  sendUser() {
    let nuevoUser = new User();
    nuevoUser.name = this.name;
    nuevoUser.email = this.email;
    nuevoUser.password = this.password;
    nuevoUser.age = parseInt(this.age);
    console.log(nuevoUser.name + ' email: ' + nuevoUser.email);
    
    this.userRestService.add(nuevoUser).subscribe(userAux => this.userInsertado = userAux);
  }
}
