<template>
  <div class="about-sections">
    <div class="section general-about">
      <img src="@/assets/about.svg" alt="About Image" class="section-image" />
      <h1>Make request to backend</h1>
      <!-- Text Input Field -->
      <textarea
        v-model="inputText"
        placeholder='Enter in json format {"username":"rajesh"}'
        maxlength="800"
      ></textarea>

      <!-- Buttons for API Requests -->
      <div class="api-buttons">
        <button
          v-for="api in apis"
          :key="api.path"
          @click="makeApiRequest(api.path)"
        >
          {{ api.label }}
        </button>

        <!-- Command Display Button -->
        <button @click="showCommandDisplay = true">Command Display</button>
      </div>

      <!-- Scrollable Text Area Modal -->
      <div v-if="showCommandDisplay" class="modal">
        <div class="modal-content">
          <span class="close" @click="showCommandDisplay = false">&times;</span>
          <textarea readonly class="command-display-textarea">
           <!--
                userView: {"username": "rajesh"},

                addFitnessClass: {"type": "yoga"},

                registerClass: {"username": "rajesh", "classType": "Yoga"},

                viewReferrer: {"username": "rajesh2"},

                viewReferree: {"username": "admin"},

                addPointsReferrer: {"username": "admin"},

                viewUnapprovedReward: {"username": "admin"},

                viewRewardPoint: {"username": "admin"},

                viewTotalPoints: {"username": "admin"},

                approveRewardPoint: {"pointId": 67, "username": "rajesh2", "pointBalance": 100},

                viewRewardRedemption: {"username": "admin"},

                redeemRewards: {"username": "rajesh", "items": "T-shirt", "point": 200}
            -->
          </textarea>
        </div>
      </div>

      <!-- Existing Modal for API Responses -->
      <div v-if="showCommandDisplay" class="modal">
        <div class="modal-content">
          <span class="close" @click="showCommandDisplay = false">&times;</span>
          <textarea readonly class="command-display-textarea">
            Content goes here...
          </textarea>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useUserStore } from "@/store/index";
import { ref } from "vue";

export default {
  setup() {
    const userStore = useUserStore();
    const inputText = ref("");
    const showCommandDisplay = ref(false);
    const showModal = ref(false);

    const baseApiUrl = "http://localhost:8000/admin";

    const apis = [
      { label: "User", path: `${baseApiUrl}/` },
      { label: "User View", path: `${baseApiUrl}/user/view` },
      { label: "User All View", path: `${baseApiUrl}/user/allview` },
      { label: "Add Fitness Class", path: `${baseApiUrl}/fitnessclass/add` },
      { label: "Fitness Class", path: `${baseApiUrl}/fitnessclass` },
      { label: "Register Class", path: `${baseApiUrl}/registerclass` },
      { label: "Referrer View", path: `${baseApiUrl}/referrer/view` },
      { label: "Referee View", path: `${baseApiUrl}/referree/view` },
      { label: "Referral View", path: `${baseApiUrl}/referral/view` },
      {
        label: "Add Referrer Points",
        path: `${baseApiUrl}/referrer/addPoints`,
      },
      {
        label: "Unapproved Reward View",
        path: `${baseApiUrl}/unapprovedreward/view`,
      },
      {
        label: "All Unapproved Rewards",
        path: `${baseApiUrl}/unapprovedreward/allview`,
      },
      { label: "Reward Point View", path: `${baseApiUrl}/rewardpoint/view` },
      {
        label: "Total Reward Points",
        path: `${baseApiUrl}/rewardpoint/totalpoints`,
      },
      {
        label: "All Reward Points View",
        path: `${baseApiUrl}/rewardpoint/allview`,
      },
      {
        label: "Approve Unapproved Reward",
        path: `${baseApiUrl}/unapprovedreward/approve`,
      },
      {
        label: "Reward Redemption View",
        path: `${baseApiUrl}/rewardredemption/view`,
      },
      {
        label: "All Reward Redemptions",
        path: `${baseApiUrl}/rewardredemption/allview`,
      },
      {
        label: "Reward Redemption",
        path: `${baseApiUrl}/rewardredemption/redemption`,
      },
    ];

    const makeApiRequest = async (path) => {
      try {
        let payload = {};

        // Check if the input is not empty
        if (inputText.value.trim() !== "") {
          try {
            // Try to parse the input text as JSON
            payload = JSON.parse(inputText.value);
          } catch (e) {
            // If parsing fails, log an error and optionally set payload to null or keep it as an empty object
            console.error(
              "Error parsing input as JSON, using empty object:",
              e
            );
          }
        }

        const response = await axios.post(path, payload, {
          headers: {
            Authorization: `Bearer ${userStore.token}`,
            "Content-Type": "application/json",
          },
        });

        alert(JSON.stringify(response.data));
      } catch (error) {
        console.error("API request failed:", error);
        alert("API request failed");
      }
    };

    return {
      inputText,
      apis,
      makeApiRequest,
      showCommandDisplay,
      showModal, // Add if not already present
      // Any other data or methods you have
    };
  },
};
</script>

<style scoped>
body,
html {
  height: 100%;
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, sans-serif;
  color: #555;
}

body {
  overflow-x: hidden;
}

.about-sections {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 40px;
}

.section {
  position: relative;
  border-radius: 12px;
  padding: 40px;
  margin-bottom: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  max-width: 800px;
  width: 100%;
  background: linear-gradient(135deg, #f5f7fa, #eef2f7);
  border: 1px solid #e1e8ed;
}

.section:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.section-image {
  position: absolute;
  top: -20px;
  right: -20px;
  width: 120px;
  border-radius: 50%;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s;
}

.section:hover .section-image {
  transform: scale(1.1);
}

h1 {
  color: #880000;
  font-size: 28px;
  margin-bottom: 24px;
}

p {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 24px;
  color: #7f8c8d;
}

ul {
  padding-left: 24px;
  list-style-type: none;
}

li {
  position: relative;
  font-size: 18px;
  margin-bottom: 14px;
  color: #34495e;
  transition: all 0.3s;
}

li:hover {
  color: #e74c3c;
  cursor: pointer;
  transform: translateX(10px);
  transition: all 0.3s;
}

li:hover .tooltip {
  visibility: visible;
}

.tooltip {
  position: absolute;
  visibility: hidden;
  background-color: rgba(231, 76, 60);
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 5px 10px;
  z-index: 1;
  bottom: 100%;
  left: 50%;
  transform: translateY(-100%);
  margin-left: 8px;
  white-space: nowrap;
  font-size: 14px;
  box-shadow: 0px 2px 10px rgba(124, 10, 10, 0.1);
}

li[data-tooltip="open"] .tooltip {
  visibility: visible;
}

@media (max-width: 740px) {
  .about-sections {
    padding: 20px, 15px;
    margin-left: 15px;
    margin-right: 15px;
  }

  .section {
    padding: 30px;
  }

  h1 {
    font-size: 24px;
  }

  p,
  li {
    font-size: 16px;
  }

  li:hover {
    transform: none;
  }

  .section-image {
    top: -20px;
    right: 5px;
    width: 60px;
  }

  .tooltip {
    bottom: auto; /* Reset the default bottom positioning */
    top: 100%; /* Position tooltip below the item on mobile */
    left: 50%;
    transform: translateX(-50%); /* Center the tooltip */
    max-width: 200px;
    white-space: normal;
  }
}

/* Additional styles for new elements */
textarea {
  width: 100%;
  min-height: 100px;
  margin-bottom: 20px;
}

.api-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.command-display-textarea {
  width: 100%;
  height: 300px; /* Fixed height for scroll */
  overflow-y: scroll; /* Enable vertical scrolling */
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 20px;
}
.api-buttons button {
  flex: 1 1 calc(100% / 4 - 10px); /* Adjust button width */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #f5f5f5;
}

.api-buttons button:hover {
  background-color: #e0e0e0;
}

.close {
  cursor: pointer;
}
</style>
