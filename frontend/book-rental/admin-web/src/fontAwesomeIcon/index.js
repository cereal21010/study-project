import Vue from "vue";

import {library} from "@fortawesome/fontawesome-svg-core";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

//설치했던 아이콘파일에서 해당 아이콘만 불러옵니다.
import {faLink, faRedo, faUndo, faBook, faAddressCard} from "@fortawesome/free-solid-svg-icons";

//불러온 아이콘을 라이브러리에 담습니다.
library.add(faAddressCard, faLink, faRedo, faUndo, faBook);

//fontawesome 아이콘을 Vue 탬플릿에 사용할 수 있게 등록해 줍니다.
Vue.component("fa-icon", FontAwesomeIcon);