import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { InventoryComponent } from './inventory/inventory.component';
import { Account } from './model/account/account.model';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { InventoryUserManagementComponent } from './inventory-user-management/inventory-user-management.component';
import { InventoryUserCreateFormComponent } from './inventory-user-management/inventory-user-create-form/inventory-user-create-form.component';
import { InventoryUserEditFormComponent } from './inventory-user-management/inventory-user-edit-form/inventory-user-edit-form.component';
import { InventoriesManagementComponent } from './inventories-management/inventories-management.component';
import { InventoryCreateFormComponent } from './inventories-management/inventory-create-form/inventory-create-form.component';
import { InventoriesEditFormComponent } from './inventories-management/inventories-edit-form/inventories-edit-form.component';
import { FindStockComponent } from './find-stock/find-stock.component';
import { ProductsManagementComponent } from './products-management/products-management.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'inventory',
    component: InventoryComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        data: { state: { accountInfo: Account } },
      },
      {
        path: 'inventory_users',
        component: InventoryUserManagementComponent,
        children: [],
      },
      {
        path: 'create_users',
        component: InventoryUserCreateFormComponent,
      },
      {
        path: 'edit_user/:id',
        component: InventoryUserEditFormComponent,
      },
      {
        path: 'inventories',
        component: InventoriesManagementComponent,
      },
      { path: 'create_inventory', component: InventoryCreateFormComponent },
      { path: 'edit_inventory/:id', component: InventoriesEditFormComponent },
      { path: 'find_stock', component: FindStockComponent },
      {
        path: 'products',
        component: ProductsManagementComponent,
      },
      { path: 'create_product', component: InventoryCreateFormComponent },
      { path: 'edit_product/:id', component: InventoriesEditFormComponent }
    ],
  },
  
  { path: 'forgot_password', component: ForgotPasswordComponent },
  { path: 'reset_password', component: ResetPasswordComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
