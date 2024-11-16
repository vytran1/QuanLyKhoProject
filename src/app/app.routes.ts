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
import { InventoryOrderComponent } from './component/inventory-order/inventory-order.component';
import { InventoryOrderCreateFormComponent } from './component/inventory-order/inventory-order-create-form/inventory-order-create-form.component';
import { InventoryOrderDetailFormComponent } from './component/inventory-order/inventory-order-detail-form/inventory-order-detail-form.component';
import { InventoryOrderEditFormComponent } from './component/inventory-order/inventory-order-edit-form/inventory-order-edit-form.component';
import { ImportingFormComponent } from './component/importing-form/importing-form.component';
import { ImportingFormCreateFormComponent } from './component/importing-form/importing-form-create-form/importing-form-create-form.component';
import { ImportingFormDetailComponent } from './component/importing-form/importing-form-detail/importing-form-detail.component';

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
      { path: 'inventory_order', component: InventoryOrderComponent },
      { path: 'create_order', component: InventoryOrderCreateFormComponent },
      {
        path: 'inventory_order_detail/:id',
        component: InventoryOrderDetailFormComponent,
      },
      {
        path: 'edit_order/:id',
        component: InventoryOrderEditFormComponent,
      },
      {
        path: 'importing_form',
        component: ImportingFormComponent,
      },
      {
        path: 'create_importing_form/:orderId',
        component: ImportingFormCreateFormComponent,
      },
      {
        path: 'importing_form_detail/:id',
        component: ImportingFormDetailComponent,
      },
    ],
  },
  { path: 'forgot_password', component: ForgotPasswordComponent },
  { path: 'reset_password', component: ResetPasswordComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
