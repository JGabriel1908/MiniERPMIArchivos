import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const rolGuard: CanActivateFn = (route) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const rolesPermitidos = route.data['roles'] as string[] | undefined;
  const rolActual = authService.getRol();

  if (!rolesPermitidos || (rolActual && rolesPermitidos.includes(rolActual))) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};
