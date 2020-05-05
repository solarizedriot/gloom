# gloom
ki rapper | modules:
textgen / flowgen / voicegen

example (current implementation): https://soundcloud.com/user-546862337/sets/fg-1

### textgen
#### task
- generate a rap text

#### current implementation (colab notebook)
- scrapes lyrics from genius.com (for gloom.v01: mf doom & aesop rock)
- generates text with gpt-2 google colab notebook with a long text file with lyrics scraped beforehand


### flowgen
#### task
- generate a rap flow

#### current implementation (java with JFugue)
- takes in a (generated) rap text
- returns a midi-file with a "flow", generated on the basis of the rap text


### voicegen
#### task
- generate a rap voice

#### current implementation (chipspeech)
- takes in a generated rap text and a midi file with the flow for the rap text (and technically a bpm number (hopefully that is decided before you generate the flow))
- returns a .wav file with the generated voice, on the basis of all inputs

#### current issue 
i've thought about using more coprehensible/understandable voice synthesizer / text to speech program. 
the problem is that voice synthesizers and text to speech programs have an important difference. voice synthesizers are not understandable but rhythmically correct, text to speech programs are understandable but not designed with rhythm in mind.
to generate good rap, you need both rythm and understandability.

#### proposed solutions
- using **utau** instead of **chipspeech**
- - somehow implementing **utau** into the workflow (e.g. replacing the current voicegen module and modifying the flowgen module to produce a flow and file for utau)
- - example: coprehensible & rhythmically good voice synthesis with **utau**: https://www.youtube.com/watch?v=1pUXLuIBCKQ

### todo:
- **glue workflow & modules together (automation)**
- improve workflow & modules

#### maybe/todo features
- everything in german (T^T)
