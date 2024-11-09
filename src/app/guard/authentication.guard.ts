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

export class AuthenticationGuard implements CanActivate {
  constructor(
    private authService: AuthenticationService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    return this.isLogged();
  }

  private isLogged(): boolean {
    if (this.authService.isLogged()) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
}
