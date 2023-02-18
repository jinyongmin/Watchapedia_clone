const {createApp} = Vue

createApp({
    data(){
        return {
            pwNoText: false,
            pwOkText: false,
            employeeOk: false,
            employeeNo: false,
            employeeDupl: false,
            idDupl: false,
            noDupl: false,
        }
    },
    methods: {
        pwLength(){
            const adminPw = document.getElementById('adminPw')

            if(adminPw.value.length <= 5){
                alert('비밀번호는 6자 이상 입력하셔야 합니다.')
                adminPw.focus()
                return false
            }
        },
        pwEquals(){
            console.log("비밀번호 확인 메소드 발동!")
            const adminPw = document.getElementById('adminPw')
            const adminPwRe = document.getElementById('adminPwRe')

            if(adminPw.value != adminPwRe.value){
                console.log('비밀번호가 달라서 if문 진입!')
                this.pwNoText = true
                this.pwOkText = false
            }else{
                console.log('비밀번호가 같아서 else문 진입!')
                this.pwNoText = false
                this.pwOkText = true
            }
        },
        reEmplCheck(){
            this.employeeOk = false
            this.employeeNo = false
            this.employeeDupl = false
        },
        employeeCheck(){
            console.log('employee 메소드 발동!')
            let flag = false
            const adminNumber = document.getElementById('adminNumber')
            console.log("입력한 사번: " + adminNumber.value)
            for (let i of employee){
                if(adminNumber.value == i){
                    console.log('동일한 사번을 찾았습니다!')
                    flag = true
                    break
                }
            }
            if(flag == true){
                this.employeeOk = true
                this.employeeNo = false
                this.employeeDupl = false
            }else{
                console.log('입력하신 사번은 없는 사번입니다!')
                this.employeeNo = true
                this.employeeOk = false
                this.employeeDupl = false
            }

            fetch('http://localhost:9090/api/admin/employeeCheck', {
                method: 'POST',
                headers: {'content-Type': 'application/json'},
                body: JSON.stringify({
                    "transaction_time":`${new Date()}`,
                    "resultCode":"ok",
                    "description":"정상",
                    "data":{
                        "adminNumber":`${adminNumber.value}`
                    }
                })
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data.resultCode)
                    if(data.resultCode == "OK"){
                        this.employeeOk = false
                        this.employeeNo = false
                        this.employeeDupl = true
                    }else {
                        console.log('중첩되지 않은 사번')
                    }
                })
        },
        reDupl(){
            this.idDupl = false
            this.noDupl = false
        },
        idCorrect(){
            this.idDupl = false
            this.noDupl = false
            const adminId = document.getElementById("adminId")

            if(adminId.value.length <= 5){
                alert('아이디는 6자 이상 입력하셔야 합니다.')
                adminId.focus()
                return false
            }

            //tb_admin_user에서 해당 값과 같은 값이 있는지 비교하는 api 개발
            fetch('http://localhost:9090/api/admin/idCheck', {
                method: 'POST',
                headers: {'content-Type': 'application/json'},
                body: JSON.stringify({
                    "transaction_time":`${new Date()}`,
                    "resultCode":"ok",
                    "description":"정상",
                    "data":{
                        "adminId":`${adminId.value}`
                    }
                })
            })
                .then(header => header.json())
                .then(data => {
                    console.log(data.resultCode)

                    if(data.resultCode == "OK"){
                        this.idDupl = true
                        this.noDupl = false

                    }else {
                        this.noDupl = true
                        this.idDupl = false
                    }
                })
        },

        updateCheck(){
            const adminId = document.getElementById("adminId")
            const adminPw = document.getElementById("adminPw")
            const adminNumber = document.getElementById("adminNumber")
            const adminName = document.getElementById("adminName")
            const adminType = document.getElementById("adminType")


            // if(this.idDupl == true || this.noDupl == false){
            //     console.log('중복 확인을 완료해주세요.')
            //     adminId.focus()
            //     return false;
            // }
            if(this.pwNoText == true){
                console.log('비밀번호가 일치하지 않음')
                adminPw.focus()
                return false;
            }

            if(adminName.value == "" || adminName.value.length < 2){
                alert('이름을 입력하십시오.')
                return false;
            }
            if(adminType.value == '관리자유형'){
                alert('관리자유형을 선택하십시오.')
                return false;
            }


            fetch('http://localhost:9090/api/admin/updateaccount', {
                method: 'POST',
                headers: {'content-Type': 'application/json'},
                body: JSON.stringify({
                    "transaction_time":`${new Date()}`,
                    "resultCode":"ok",
                    "description":"정상",
                    "data":{
                        "adminIdx":`${adminIdx.value}`,
                        "adminId":`${adminId.value}`,
                        "adminPw":`${adminPw.value}`,
                        "adminNumber":`${adminNumber.value}`,
                        "adminName":`${adminName.value}`,
                        "adminType":`${adminType.value}`,
                    }
                })
            })
                .then((response) => response.json())
                .then((data) => {
                    if (data.resultCode == 'OK') {
                        alert('변경 성공했습니다! ');
                        location.href=`/hradmin/${adminIdx.value}`;
                    } else {
                        alert('수정에 실패했습니다. 다시 한번 확인해주세요! ')
                    }
                })
                .catch((err) => {
                    alert('에러발생');
                    location.reload();
                });
        }
    }
}).mount('#main_box')