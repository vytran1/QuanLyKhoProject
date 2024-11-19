import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateFn,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { Role } from '../../environments/role.enum';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    if (!this.authService.isLogged()) {
      this.router.navigate(['/login']);
      return false;
    }
    // this.authService.setRole();
    //console.log('Required Role');
    const requiredRole: Role | null = route.data['requiredRole'] || null;
    //console.log(requiredRole);

    if (!requiredRole) {
      return true;
    }

    if (
      (requiredRole === Role.COMPANY && this.authService.isCompany()) ||
      (requiredRole === Role.EMPLOYEE && this.authService.isEmployee())
    ) {
      return true;
    }

    if (this.authService.isCompany()) {
      //console.log('You have role Company');
    } else {
      //console.log('You have role Employee');
    }

    this.router.navigate(['/inventory/unauthorized']);
    return false;
  }

  // private isLogged(): boolean {
  //   if (this.authService.isLogged()) {
  //     return true;
  //   }
  //   this.router.navigate(['/login']);
  //   return false;
  // }
}
