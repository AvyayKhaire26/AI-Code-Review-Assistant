import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnvironmentService {
  
  get apiUrl(): string {
    return 'http://localhost:8080/api';
  }

  get apiTimeout(): number {
    return 30000; // 30 seconds for AI processing
  }

  get production(): boolean {
    return false;
  }
}
