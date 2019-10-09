import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from 'src/model/user';

@Injectable({
  providedIn: 'root'
})
export class UsuarioRestService {
  uriApiRest = 'http://localhost:8084/CRUD_Vista_JSTL/api/usuarios';
  httpOption = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private httpCli: HttpClient) { }

  getUsuarios(): Observable<User[]> {
    let observerResp: Observable<User[]>;
    observerResp = this.httpCli.get<User[]>(this.uriApiRest);

    return observerResp;
  }
  add(nuevoUser: User): Observable<User> {
    return this.httpCli.post<User>(this.uriApiRest, nuevoUser, this.httpOption);

  }

}
