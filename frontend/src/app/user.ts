export class User {
  constructor(
    public id: number,
    public name: string,
    public sectorsIds: number[],
    public termsAgreed: boolean
  ) {  }

}
