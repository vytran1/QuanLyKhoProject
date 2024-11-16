import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AccountInformationService } from '../service/account-information.service';
import { Subscription } from 'rxjs';
import { Account } from '../model/account/account.model';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.css',
})
export class InventoryComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  accountInfo: Account = {};
  profileImage: SafeUrl | null = null;
  constructor(
    private accountService: AccountInformationService,
    private sanitizer: DomSanitizer,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    //Lấy Data
    this.loadData();

    //Đăng ký sự kiện để cập nhật value latest
    this.subscriptions.push(
      this.accountService.accountInfo$.subscribe((accountData) => {
        if (accountData) {
          this.accountInfo = accountData;
        }
      })
    );

    //Đăng ký sự kiện để cập nhật ảnh value latest
    this.subscriptions.push(
      this.accountService.profileImage$.subscribe((imageUrl) => {
        if (imageUrl) {
          console.log('New Image');
          console.log(imageUrl);
          this.profileImage = this.sanitizer.bypassSecurityTrustUrl(imageUrl);
        }
      })
    );
  }

  loadData() {
    this.subscriptions.push(
      this.accountService.getPersonalInfo().subscribe({
        next: (response) => {
          console.log(response);
          const body = response.body;
          this.accountInfo = body as Account;
          this.accountService.setAccountInfo(this.accountInfo);
          this.loadImage();
        },
        error: (error) => {
          console.error('Error fetching personal information:', error);
        },
      })
    );
  }

  loadImage() {
    this.subscriptions.push(
      this.accountService.getPersonalImage().subscribe({
        next: (imageBlog) => {
          const objectUrl = URL.createObjectURL(imageBlog);
          this.profileImage = this.sanitizer.bypassSecurityTrustUrl(objectUrl);
          this.accountService.setProfileImage(objectUrl);
        },
        error: (error) => {
          console.error('Error fetching profile image:', error);
        },
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => subscription.unsubscribe);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
