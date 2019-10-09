import { Component, OnInit } from '@angular/core';
import { User } from 'src/model/user';
import { UsuarioRestService } from '../usuario-rest.service';
import { Observable } from 'rxjs';

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
  id: string;
  name: string;
  age: string;
  email: string;
  password: string;
  constructor(private userRestService: UsuarioRestService) { }

  ngOnInit() {
    this.getUsuariosAPI();
    this.modoEdicion = false;
    this.modoBorrar = false;
  }
  
  getUsuariosAPI()
  {
    let observerArray = this.userRestService.getUsuarios();

    let funcionRellena = (usuariosRecib) => {
      this.users = usuariosRecib;
    }
    observerArray.subscribe(funcionRellena);
  }
  onSelect(user: User) {
    this.userSelected = user;

  }
  enableEdition() {
    this.modoEdicion = !this.modoEdicion;
    if(this.modoBorrar) {
      this.modoBorrar = false;
    }
  }
  enableDeleteById() {
    this.modoBorrar = !this.modoBorrar;
    if(this.modoEdicion) {
      this.modoEdicion = false;
    }
  }
  
  sendUser() {
    let nuevoUser = new User();
    nuevoUser.name = this.name;
    nuevoUser.email = this.email;
    nuevoUser.password = this.password;
    nuevoUser.age = parseInt(this.age);
    
    this.userRestService.add(nuevoUser).subscribe(userAux => this.userInsertado = userAux);
    this.getUsuariosAPI();
    this.enableEdition();
  }
  
  update(){
    let nuevoUser = new User();
    nuevoUser.id = parseInt(this.id);
    nuevoUser.name = this.name;
    nuevoUser.email = this.email;
    nuevoUser.password = this.password;
    nuevoUser.age = parseInt(this.age);
    
    this.userRestService.update(nuevoUser).subscribe(userAux => this.userInsertado = userAux);
  }
}
