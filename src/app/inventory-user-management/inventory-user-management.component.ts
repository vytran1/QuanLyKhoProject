import { Component, OnDestroy, OnInit } from '@angular/core';
import { InventoryUser } from '../model/inventory-user/inventory-user.model';
import { InventoryUserManagementService } from '../service/inventory-user-management.service';
import { Subscription } from 'rxjs';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { PaginationComponent } from '../pagination/pagination.component';
import { SearchComponent } from '../search/search.component';
import { ChangePageSizeComponent } from '../change-page-size/change-page-size.component';
import { CommonModule } from '@angular/common';
import { InventoryUserDetailModalComponent } from './inventory-user-detail-modal/inventory-user-detail-modal.component';
import { DeleteInventoryUserWarningModalComponent } from './delete-inventory-user-warning-modal/delete-inventory-user-warning-modal.component';
import { MessageModalComponent } from '../message-modal/message-modal.component';
import { Router, RouterModule } from '@angular/router';
import { ImprovedPaginationComponent } from '../improved-pagination/improved-pagination.component';
import { UploadExcelFileComponent } from '../sub-component/upload-excel-file/upload-excel-file.component';

@Component({
  selector: 'app-inventory-user-management',
  standalone: true,
  imports: [
    ImprovedPaginationComponent,
    SearchComponent,
    ChangePageSizeComponent,
    CommonModule,
    InventoryUserDetailModalComponent,
    DeleteInventoryUserWarningModalComponent,
    MessageModalComponent,
    RouterModule,
    UploadExcelFileComponent,
  ],
  templateUrl: './inventory-user-management.component.html',
  styleUrl: './inventory-user-management.component.css',
})
export class InventoryUserManagementComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  inventoryUsers: InventoryUser[] = [];
  pageNum: number = 1;
  pageSize: number = 2;
  sortField: string = 'userId';
  sortDir: string = 'asc';
  totalPage: number = 1;
  totalItems: number = 1;
  profileImages: { [userId: string]: SafeUrl } = {};
  //Detail
  selectedEmployee: InventoryUser | null = null;
  showModal: boolean = false;
  //Delete
  showModalDelete: boolean = false;
  deleteMessage = 'Are your sure to delete this user';
  deletedEmployee: InventoryUser | null = null;
  //MessageModal
  showModalMessage: boolean = false;
  message = '';
  type: 'success' | 'error' = 'success';
  //UploadModal
  showModalUpload = false;

  constructor(
    private inventoryUserService: InventoryUserManagementService,
    private santinizer: DomSanitizer,
    private router: Router
  ) {}

  ngOnInit(): void {
    //this.initialData();
    const subscription = this.inventoryUserService.users$.subscribe((users) => {
      this.inventoryUsers = users;
      this.loadImage();
    });

    const totalPagesSubcription =
      this.inventoryUserService.totalPage$.subscribe((totalPage) => {
        this.totalPage = totalPage;
      });

    const totalItemsSubscription =
      this.inventoryUserService.totalItem$.subscribe((totalItem) => {
        this.totalItems = totalItem;
      });

    this.subscriptions.push(subscription);
    this.subscriptions.push(totalItemsSubscription);
    this.subscriptions.push(totalPagesSubcription);
    //this.initialData();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subcription) => subcription.unsubscribe());
  }

  setPageNum(pageNum: any): void {
    this.inventoryUserService.setPageNum(pageNum);
  }
  setPageSize(pageSize: number): void {
    this.inventoryUserService.setPageSize(pageSize);
  }

  setSortField(sortField: string): void {
    this.inventoryUserService.setSortField(sortField);
  }

  setSortDir(sortDir: string): void {
    this.inventoryUserService.setSortDir(sortDir);
  }

  initialData() {
    this.subscriptions.push(
      this.inventoryUserService.getFirstPage().subscribe({
        next: (response) => {
          console.log(response);
          if (response.body) {
            const body = response.body;
            this.inventoryUsers = body.inventoryUsers;
            this.totalItems = body.totalItems;
            this.totalPage = body.totalPage;
            this.loadImage();
          }
        },
        error: (error) => {
          console.log(error);
        },
      })
    );
  }

  loadImage() {
    this.inventoryUsers.forEach((user) => {
      this.subscriptions.push(
        this.inventoryUserService.getProfileImage(user.userId).subscribe({
          next: (response) => {
            const objectUrl = URL.createObjectURL(response);
            this.profileImages[user.userId] =
              this.santinizer.bypassSecurityTrustUrl(objectUrl);
          },
        })
      );
    });
  }

  getProfileImage(userId: string): SafeUrl | undefined {
    return this.profileImages[userId];
  }

  onChangePage(event: any) {
    this.setPageNum(event);
  }
  onSearch(event: any) {
    this.inventoryUserService.setKeyWord(event);
  }
  onPageSizeChange(event: any) {
    this.setPageSize(event);
  }

  changeSort(field: string) {
    if (this.sortField === field) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDir = 'asc';
    }
    this.inventoryUserService.setSortFieldAndDir(this.sortField, this.sortDir);
  }
  addEmployee() {
    this.router.navigate(['/inventory/create_users']);
  }

  showEmployeeDetail(user: InventoryUser) {
    this.selectedEmployee = user;
    this.showModal = true;
  }
  closeEmployeeDetail() {
    this.showModal = false;
    this.selectedEmployee = null;
  }

  openDeleteModal(deleteUser: InventoryUser) {
    this.showModalDelete = true;
    this.deletedEmployee = deleteUser;
  }

  confirmDeleteOperation() {
    console.log('Delete');
    this.subscriptions.push(
      this.inventoryUserService
        .deleteUser(this.deletedEmployee?.userId)
        .subscribe({
          next: (response) => {
            console.log('Success Delete');
            //Make some toast thành công delete
            this.message = 'Success Delete User';
            this.type = 'success';
            this.deletedEmployee = null;
            this.cancelDeleteOperation();
            this.showModalMessage = true;
            //Load loại dữ liệu mới cho inventoryUsers
            this.loadUserList();
          },
          error: (error) => {
            console.log('Error Delete');
            //Error 400 Not found => không tồn tại nhân viên với mã ....
            //Make some toast hiển thị error trên
            if (error.status === 400) {
              this.message =
                'Fail to delete user because there are no user exist with your given id';
              this.type = 'error';
            } else {
              this.message = 'Internal sever error';
              this.type = 'error';
            }
            this.deletedEmployee = null;
            this.cancelDeleteOperation();
            this.showModalMessage = true;
          },
        })
    );
  }

  cancelDeleteOperation() {
    this.showModalDelete = false;
  }

  loadUserList() {
    this.subscriptions.push(
      this.inventoryUserService
        .listByPage(this.pageNum, this.pageSize, this.sortField, this.sortDir)
        .subscribe({
          next: (response) => {
            if (response.body) {
              const body = response.body;
              this.inventoryUsers = body.inventoryUsers;
              this.totalItems = body.totalItems;
              this.totalPage = body.totalPage;
              this.loadImage();
            }
          },
          error: (error) => {
            console.log(error);
          },
        })
    );
  }

  closeModelMessage() {
    this.showModalMessage = false;
  }

  exportToExcel() {
    this.subscriptions.push(
      this.inventoryUserService.exportToExcel().subscribe({
        next: (response) => {
          const blob = new Blob([response.body!], {
            type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
          });
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'users.xlsx'; // Đặt tên file tải về
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url); // Hủy URL tạm thời sau khi tải xong

          // Hiển thị thông báo thành công
          this.message =
            'Xuất Danh Sách Bằng File Excel Thành Công. Hãy kiểm tra thư mục Downloads của bạn.';
          this.type = 'success';
          this.showModalMessage = true;
        },
        error: (error) => {
          console.log(error);
          this.message = 'Xuất Danh Sách Bằng File Excel Thất Bại!';
          this.type = 'error';
          this.showModalMessage = true;
        },
      })
    );
  }
  showModalUploadExcel() {
    this.showModalUpload = true;
  }

  closeModalUpLoadExcel() {
    this.showModalUpload = false;
  }

  createMultipleUserThroughExcelFile(file: File) {
    console.log('Submit From Parent Component');
    console.log(file);
    this.subscriptions.push(
      this.inventoryUserService.createMultipleUserByExcelFile(file).subscribe({
        next: (response) => {
          (this.message = 'Tạo nhiều user thông qua file excel thành công'),
            (this.type = 'success');
          this.showModalMessage = true;
          this.inventoryUserService.setPageNum(1);
        },
        error: (error) => {
          console.log(error);
          (this.message = 'Tạo nhiều user thông qua file excel thất bại'),
            (this.type = 'error'),
            (this.showModalMessage = true);
        },
      })
    );
  }
}
