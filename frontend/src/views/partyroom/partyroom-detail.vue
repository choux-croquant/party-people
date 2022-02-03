<template>
  <div class="h-screen w-screen flex bg-tc-500">
    <div class="fixed inset-0 flex z-40">
      <room-sidebar></room-sidebar>
      <div id="session" v-if="session">
        <div id="session-header">
					<div class="mx-auto">
						<timer @startCountdown="startCountdown" ref="timer"></timer>
				</div>
        </div>
        <div id="video-container" class="grid grid-cols-3 gap-2">
          <user-video :stream-manager="publisher"/>
          <user-video v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub"/>
        </div>
      </div>
      <room-chat @message="sendMessage" ref="chat" :subscribers="subscribers"></room-chat>
      <room-bottombar @audioOnOff="audioOnOff" @videoOnOff="videoOnOff" @leaveSession="leaveSession()" ></room-bottombar>
    </div>
  </div>
</template>
<style>
</style>
<script>
  
import roomSidebar from './components/room-sidebar.vue'
import RoomChat from './components/room-chat.vue'
import UserVideo from './components/user-video.vue'
import { OpenVidu } from 'openvidu-browser'
import axios from 'axios';
import { useRouter } from 'vue-router'
import roomBottombar from './components/room-bottombar.vue'
import timer from './components/timer.vue'

const OPENVIDU_SERVER_URL = "https://pparttypeople.kro.kr:4443";
const OPENVIDU_SERVER_SECRET = "a106ssafy0183";

export default {
  components: { roomSidebar, RoomChat, UserVideo, timer,  roomBottombar },
  name: 'conference-detail',
  props: {
    conferenceId: {
      type: Number
    },
    userName: {
      type: String
    }
  },
  data () {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: this.conferenceId,
			myUserName: this.userName,
      router: useRouter()
		}
	},
  methods: {
		joinSession () {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);

				// subscriber.userId = this.myUserName;  // subscriber Object에 userName 추가
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// public 채팅 signal 받기
			this.session.on('signal:public-chat', (event) => {
				this.$refs.chat.addMessage(event.data, JSON.parse(event.data).sender === this.myUserName, false)
			})

			// private 채팅 signal 받기
			this.session.on('signal:private-chat', (event) => {
				this.$refs.chat.addMessage(event.data, false, true)
			})

			// 타이머 signal 받기
			this.session.on('signal:timer', (event) => {
				this.$refs.timer.startCountdown(event.data)
			})
			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session.connect(token, { clientData: this.myUserName })
					.then(() => {

						// --- Get your own camera stream with the desired properties ---

						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true,  	// Whether you want to start publishing with your audio unmuted or not
							publishVideo: true,  	// Whether you want to start publishing with your video enabled or not
							resolution: '640x480',  // The resolution of your video
							frameRate: 30,			// The frame rate of your video
							insertMode: 'APPEND',	// How the video is inserted in the target element 'video-container'
							mirror: false,       	// Whether to mirror your local video or not
						});

						publisher.stream.userName = 'asdfasdf'

						this.mainStreamManager = publisher;
						this.publisher = publisher;
            console.log(this.publisher)
						// --- Publish your stream ---

						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log('There was an error connecting to the session:', error.code, error.message);
					});
			});

			window.addEventListener('beforeunload', this.leaveSession)
		},

    leaveSession () {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			window.removeEventListener('beforeunload', this.leaveSession);
      this.router.push({name: 'Home'})
		},
    getToken (mySessionId) {
			return this.createSession(mySessionId).then(sessionId => this.createToken(sessionId));
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`, JSON.stringify({
						customSessionId: sessionId,
					}), {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`);
							if (window.confirm(`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`)) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken (sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`, {}, {
						auth: {
							username: 'OPENVIDUAPP',
							password: OPENVIDU_SERVER_SECRET,
						},
					})
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

		sendMessage ({ content, to }) {
			let now = new Date()
			let current = now.toLocaleTimeString([], {
				hour: '2-digit',
				minute: '2-digit',  
				hour12: false,      // true인 경우 오후 10:25와 같이 나타냄.
			})

			let messageData = {
				content: content,
				sender: this.myUserName,
				time: current,
			}

			// 전체 메시지
			if (to === "all") {
				this.session.signal({
					data: JSON.stringify(messageData),
					to: [],
					type: 'public-chat',
				})
				.then(() => {
					console.log('메시지 전송 완료')
				})
				.catch((error) => {
					console.log(error)
				})
			}

			// 개인 메시지
			if (to !== "all") {
				this.session.signal({
					data: JSON.stringify(messageData),
					to: [to],
					type: 'private-chat',
				})
				.then(() => {
					// 내가 보낸 개인 메시지 추가
					this.$refs.chat.addMessage(JSON.stringify(messageData), true, true)
					console.log('메시지 전송 완료')
				})
				.catch((error) => {
					console.log(error)
				})
			}
		},

		startCountdown ({ timer }) {
			this.session.signal({
				data: JSON.stringify(timer),
				to: [],
				type: 'timer'
			})
			.then(() => {
				console.log('타이머 전송 완료')
			})
			.catch((error) => {
				console.log(error)
			})
		},

		audioOnOff ({ audio }) {
			console.log("audio")
			this.publisher.publishAudio(audio)
		},

		videoOnOff ({ video }) {
			console.log("video")
			this.publisher.publishVideo(video)
		}

  },
  mounted() {
    console.log('mounted')
    this.joinSession()
  },
	beforeUnmount() {
		console.log('unmount')
		this.leaveSession()
	}
}
</script>
