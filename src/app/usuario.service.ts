import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from 'src/model/user';
import { MensajesService } from './mensajes.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { USUARIOS } from 'src/model/array-pruebas';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {


  constructor(private logMensajes: MensajesService) { }
  getUsuarios(): Observable<User[]> {
    this.logMensajes.add('Recibidos Todos los usuarios');
    return of(USUARIOS);
  }
  getUsuarioById(id: number): Observable<User> {
    this.logMensajes.add(`Recibido el  ${id} correctamente`);
    return of(USUARIOS.find(user => user.id === id));
  }
}
