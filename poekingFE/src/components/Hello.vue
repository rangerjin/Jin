<template>
<div class="hello">
<b-card
header="오늘 해야 할 일"
style="max-width: 40rem; margin: auto; margin-top: 10vh;"
class="mb-2"
border-variant="info"
align="left">

<b-form-group id="to-do-input">
<b-container fluid>
<b-row class="my-1">
<b-col sm="10">
<b-form-input v-model="newToDoItemRequest.title" type="text"
placeholder="새 할 일을 적으세요." v-on:keyup.enter="createToDo"/>
</b-col>
<b-col sm="2">
<b-button variant="outline-primary" v-on:click="createToDo">추가</b-button>
</b-col>
</b-row>
</b-container>
</b-form-group>

<b-list-group v-if="toDoItems && toDoItems.length">
<b-list-group-item
v-for="toDoItem of toDoItems"
v-bind:data="toDoItem.title"
v-bind:key="toDoItem.id" style="display: flex;">
<b-form-checkbox v-model="toDoItem.done" v-on:change="makeDone(toDoItem)">
</b-form-checkbox>
<span v-if="toDoItem.done" style="text-decoration: line-through; color:#D3D3D3;">{{toDoItem.title}}</span>
<span v-else>{{toDoItem.title}}</span>
</b-list-group-item>
</b-list-group>
</b-card>
</div>
</template>

<script>
import axios from 'axios'; // 아까 받은 axios 패키지를 사용하기 위해 import한다

let baseURL = 'http://poeking-lb-887821775.ap-northeast-2.elb.amazonaws.com:5000/';
export default {
  name: 'hello',
  data: () => {
    return {
      toDoItems: [], // toDoItems를 빈 리스트로 초기화한다.
      newToDoItemRequest: {} // 새 toDoItem 담는곳
    };
  },
  methods: {
    initToDoList: function () {
      let vm = this;
      axios.get(baseURL)
        .then(response => {
          vm.toDoItems = response.data.map(r => r.data);
        })
        .catch(e => {
          console.log('error : ', e);
        });
    },
    createToDo: function (event) {
      event.preventDefault();
      let vm = this;
      if (!vm.newToDoItemRequest.title) return; // To Do Item의 제목이 없으면 아무것도 안하고 리턴

      console.log(vm.newToDoItemRequest);

      axios.post(baseURL, vm.newToDoItemRequest) // 아니면 스프링 부트 RESTful API에 POST 콜
        .then(response => {
          console.log(response);
          vm.initToDoList();
          vm.newToDoItemRequest = {};
        })
        .catch(error => {
          console.log(error);
        });
    },
    makeDone: function (toDoItem) {
      if (!toDoItem) return;
      let vm = this;
      toDoItem.done = !toDoItem.done;
      axios.put(baseURL, toDoItem)
        .then(response => {
          vm.initToDoList();
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  created () { // 초기화 함수를 정의 한다.
    this.initToDoList();
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #35495E;
}
</style>