import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'advStatus'
})
export class AdvStatusPipe implements PipeTransform {

  transform(value: number): string {
    return value == 1?"В обработке":value==2?"Активный":"В архиве";
  }

}
