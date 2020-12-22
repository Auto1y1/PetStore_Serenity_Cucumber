Feature: Pet API Create, Read , Update and Delete Operations
  In order to Add new Pet using POST request on the pet api
  In order to select the type of Pet I want to search using GET request
  In order to delete Pet using DELETE request on the pet api
  In order to update the Pet details using PUT request on the pet api

  Scenario Outline: A api GET call with Pet API with valid end point and parameters as petId should returns pet details
    Given get endpoint of Pet API
    When a request is sent to Pet API with pet id <PET ID>
    Then Pet API returns response response code <ResponseCode>
    Then Pet API returns the valid pet details of <name> and <status>
    Examples:
      | PET ID |ResponseCode|name                |status         |
      |10      | 200        | shadow             |    available  |
      |5       | 200        | My Updated Pet Name|    pending    |

  Scenario Outline: A api GET call with Pet API with valid end point and incorrect parameters
    Given get endpoint of Pet API
    When a request is sent to Pet API with pet id <PET ID>
    Then Pet API returns response response code <ResponseCode>
    Then Pet API returns Error Message <ErrorMessage>
    Examples:
      | PET ID | ResponseCode |ErrorMessage  |
      | 1000   |404           |Pet not found |

  #@DryRunOnly
  Scenario Outline: A api GET call with Pet API with valid end point and correct status parameters
    Given get endpoint of Pet API
    When a request is sent to Pet API with pet Status <Status>
    Then Pet API returns response response code <ResponseCode>
    Then Validate the Pet <name>
    Examples:
      | Status      | ResponseCode |name   |
      | sold        |200           |Gracie |
      | available   |200           |doggie |

  #@DryRunOnly
  Scenario Outline: A api POST call with Pet API with valid end point and correct body parameters to add new pet
    Given get endpoint of Pet API
    When I add the pet to the store with details <name> , <status> and <PhotoURL>
    Then The Pet details should be present with <name> , <status>
    Examples:
      |name        |status       |PhotoURL          |
      | Sandra     | sold        |     SandraCopy   |
      | Tella      | available   |     TellaPics    |
      | Snead      | pending     |     SneadSpecs   |

  #@DryRunOnly
  Scenario Outline: A api Put call with Pet API with valid end point and correct body parameters to add new pet
    Given get endpoint of Pet API
    When I add the pet to the store with details <name> , <status> and <PhotoURL>
    Then The Pet details should be present with <name> , <status>
    Then I have updated the <tagName> and <CategoryName>
    Examples:
      |name    |status    |PhotoURL         |tagName        | CategoryName       |
      | Andra  |sold      |     AndraCopy   |Andra_sold     | Labrador Retriever |
      | Sella  |available |     SellaPics   |Sella_available| Golden Retriever   |
      | Dean   |pending   |     DeanSpecs   |Dean_pending   | BullDog            |

  @DryRunOnly
  Scenario Outline: A api Delete call with Pet API with valid end point and correct body parameters to add new pet
    Given get endpoint of Pet API
    When I add the pet to the store with details <name> , <status> and <PhotoURL>
    Then The Pet details should be present with <name> , <status>
    When I have deleted the Newly Added Pet
    Then The Pet details are not present
    Examples:
      |name        |status        |PhotoURL      |
      | Danny      |   available  |     Golden   |
