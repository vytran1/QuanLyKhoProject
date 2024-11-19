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
import { InventoryOrderComponent } from './component/inventory-order/inventory-order.component';
import { InventoryOrderCreateFormComponent } from './component/inventory-order/inventory-order-create-form/inventory-order-create-form.component';
import { InventoryOrderDetailFormComponent } from './component/inventory-order/inventory-order-detail-form/inventory-order-detail-form.component';
import { InventoryOrderEditFormComponent } from './component/inventory-order/inventory-order-edit-form/inventory-order-edit-form.component';
import { ImportingFormComponent } from './component/importing-form/importing-form.component';
import { ImportingFormCreateFormComponent } from './component/importing-form/importing-form-create-form/importing-form-create-form.component';
import { ImportingFormDetailComponent } from './component/importing-form/importing-form-detail/importing-form-detail.component';
import { ExportingFormComponent } from './component/exporting-form/exporting-form.component';
import { ExportingFormDetailComponent } from './component/exporting-form/exporting-form-detail/exporting-form-detail.component';
import { ExportingFormCreateFormComponent } from './component/exporting-form/exporting-form-create-form/exporting-form-create-form.component';
import { ReportComponent } from './component/report/report.component';
import { ProductCreateFormComponent } from './products-management/products-create-form/product-create-form.component';
import { ProductsEditFormComponent } from './products-management/products-edit-form/products-edit-form.component';
import { UnauthorizedComponent } from './sub-component/unauthorized/unauthorized.component';
import { Role } from '../environments/role.enum';
import { AuthenticationGuard } from './guard/authentication.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'inventory',
    component: InventoryComponent,
    canActivate: [AuthenticationGuard],
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
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'create_users',
        component: InventoryUserCreateFormComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'edit_user/:id',
        component: InventoryUserEditFormComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'inventories',
        component: InventoriesManagementComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'create_inventory',
        component: InventoryCreateFormComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'edit_inventory/:id',
        component: InventoriesEditFormComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      { path: 'find_stock', component: FindStockComponent },

      {
        path: 'products',
        component: ProductsManagementComponent,
        canActivate: [AuthenticationGuard],
      },

      {
        path: 'create_product',
        component: ProductCreateFormComponent,
        canActivate: [AuthenticationGuard],
      },

      {
        path: 'edit_product/:id',
        component: ProductsEditFormComponent,
        canActivate: [AuthenticationGuard],
      },

      {
        path: 'inventory_order',
        component: InventoryOrderComponent,
        canActivate: [AuthenticationGuard],
      },

      {
        path: 'create_order',
        component: InventoryOrderCreateFormComponent,
        canActivate: [AuthenticationGuard],
      },

      {
        path: 'inventory_order_detail/:id',
        component: InventoryOrderDetailFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'edit_order/:id',
        component: InventoryOrderEditFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'importing_form',
        component: ImportingFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'create_importing_form/:orderId',
        component: ImportingFormCreateFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'importing_form_detail/:id',
        component: ImportingFormDetailComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'exporting_form',
        component: ExportingFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'exporting_form_detail/:id',
        component: ExportingFormDetailComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'create_exporting_form',
        component: ExportingFormCreateFormComponent,
        canActivate: [AuthenticationGuard],
      },
      {
        path: 'report',
        component: ReportComponent,
        canActivate: [AuthenticationGuard],
        data: { requiredRole: Role.COMPANY },
      },
      {
        path: 'unauthorized',
        component: UnauthorizedComponent,
      },
    ],
  },

  { path: 'forgot_password', component: ForgotPasswordComponent },
  { path: 'reset_password', component: ResetPasswordComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login', pathMatch: 'full' },
];
