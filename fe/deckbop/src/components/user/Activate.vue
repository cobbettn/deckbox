<script>
import { activateUrl, jsonContentHeader } from '../../config/api';
export default {
  render() {
    return this.makeActivationRequest()
  },
  methods: {
    makeActivationRequest() {
      const reqBody = {
        activation_token: this.$route.params.id
      }
      this.$http.post(
        activateUrl,
        reqBody,
        jsonContentHeader
      )
      .then(() => {
        // TODO: dispaly success message on login
        this.$router.push("/login")
      })
      .catch((e) => {
        console.log("activation error", e)
      })
    }
  }
}
</script>
