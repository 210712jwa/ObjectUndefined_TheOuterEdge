import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Form } from '../../model/form'
import { Users } from 'src/model/user';
import { LoginService } from '../login.service';
import { FormService } from '../form.service';
import { Observable, Subject } from 'rxjs';


@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.css']
})
export class PostFormComponent implements OnInit {
  public title:string = '';
  public description:string = '';
  public latitude:any;
  public longitude:any;
  public image:any;
  public user:any;
  public form:any;


  constructor(private router:Router, private ls:LoginService, private fs:FormService) { 
/*     this.form.title = this.title;
    this.form.description = this.description;
    this.form.latitude = this.latitude;
    this.form.longitude = this.longitude; */
  }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(){
    this.ls.checkIfLoggedIn().subscribe((data:Users) => {
      this.user = data;
    })
  }


 
  post(){
    const postForm = {
       'title':this.title,
       'description':this.description,
       'latitude':this.latitude,
       'longitude':this.longitude,
    }
    return new Promise(resolve => {
    this.fs.post(postForm, this.user).subscribe((data:Form) => {
       this.form = data; 
      resolve(this.form)
    });
    });
  } 

  postImage(){
    const formData = new FormData();
    formData.append('image', this.image);
    this.fs.postImage(formData, this.user, this.form.id).subscribe((data:Form) => {
      this.router.navigate(['']);
    })
    
  }
  

  async postWithImage(){
    this.post().then(data => {
    if(this.image !== undefined){
      this.postImage();
    }else{
      this.router.navigate(['']);
    }
  })   
  }
}
