## 간단한 OS 만들기 - assembly
printf가 되는 os 만들기
BIOS의 부팅 과정, MBR, assembler를 살펴보고 Bootloader, 32bit-kernel, os를 만들었다.

ubuntu를 가상머신으로 하여 assembly 언어 사용

+ assembler 생성
![Assembler생성명령어](./1-assembler명령어.JPG)
![Assembler생성코드](./1-assembler코드.JPG)

+ bootloader 생성
![bootloader](./1-bootloader.JPG)
![bootloader명령어](./1-bootloader명령어.JPG)

+ entry.asm
![entry1](./3-entry1.JPG)
![entry2](./3-entry2.JPG)

+ 32-bit Kernel
![Kernel](./3-kernel.JPG)

+ 메모리 매핑
+ cursor 위치 조정 - vga ( vidio graphics adapter ) 적용
![vgh.h](./4-vgh.JPG)
![vgh1](./4-vgh1.JPG)
![vgh2](./4-vgh2.JPG)
![vgh3](./4-vgh3.JPG)
![vgh4](./4-vgh4.JPG)

+ kernel update
![Kernel](./4-kernel.JPG)

+ Makefile로 실행
![makefile](./4-makefile.JPG)

+ 실행결과
![run](./4-run.JPG)
![run2](./4-run2.JPG)
