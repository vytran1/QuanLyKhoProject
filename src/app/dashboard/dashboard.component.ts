import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Account } from '../model/account/account.model';
import { AccountInformationService } from '../service/account-information.service';
import { PersonalInformationComponent } from './personal-information/personal-information.component';
import { UpdatePasswordComponent } from './update-password/update-password.component';
import { UpdateImageComponent } from './update-image/update-image.component';
declare var bootstrap: any;
@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    PersonalInformationComponent,
    UpdatePasswordComponent,
    UpdateImageComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit, AfterViewInit {
  accountInfo: Account | null = {};

  constructor(private accountService: AccountInformationService) {}

  ngOnInit(): void {
    this.accountService.accountInfo$.subscribe((account) => {
      this.accountInfo = account;
      if (this.accountInfo) {
        console.log(this.accountInfo.email);
      } else {
        console.log('Null object');
      }
    });
  }

  ngAfterViewInit(): void {
    const tabTriggerList = document.querySelectorAll('#myTab button');

    // Kiểm tra xem phần tử có tồn tại không
    if (tabTriggerList.length > 0) {
      tabTriggerList.forEach((tabTriggerElement) => {
        // Ép kiểu phần tử về HTMLButtonElement
        const tabTrigger = new bootstrap.Tab(tabTriggerElement as HTMLElement);
        tabTriggerElement.addEventListener('click', function (event: Event) {
          event.preventDefault();
          tabTrigger.show();
        });
      });
    } else {
      console.log('No buttons found in #myTab');
    }
  }
}
